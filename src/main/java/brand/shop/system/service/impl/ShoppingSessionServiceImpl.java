package brand.shop.system.service.impl;

import brand.shop.system.dto.*;
import brand.shop.system.errors.ProductAmountNotEnoughException;
import brand.shop.system.errors.UserNotFoundException;
import brand.shop.system.helper.AppCode;
import brand.shop.system.helper.AppMessage;
import brand.shop.system.helper.DateUtil;
import brand.shop.system.mapper.ShoppingSessionMapper;
import brand.shop.system.mapper.UserCustomMapper;
import brand.shop.system.model.ShoppingSession;
import brand.shop.system.redis.OrderSession;
import brand.shop.system.redis.OrderSessionRepository;
import brand.shop.system.repository.OrderDetailsRepository;
import brand.shop.system.repository.ShoppingSessionRepository;
import brand.shop.system.service.main.CartItemService;
import brand.shop.system.service.main.ProductService;
import brand.shop.system.service.main.ShoppingSessionService;
import com.google.common.util.concurrent.AtomicDouble;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShoppingSessionServiceImpl implements ShoppingSessionService {

    private final ShoppingSessionRepository shoppingSessionRepository;
    private final ProductService productService;
    private final OrderSessionRepository orderSessionRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final CartItemService cartItemService;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public ResponseDto<Boolean> addShoppingSession(List<CartItemDto> list) {
        try {
            Integer userId;
            if(!(userId = hasUserOldSession()).equals(-1))
                removeOldSession(userId);

            AtomicDouble total = new AtomicDouble();
            ShoppingSession shoppingSession = buildSession();
            shoppingSessionRepository.save(shoppingSession);

            ShoppingSessionDto shoppingSessionDto = ShoppingSessionMapper.toDto(shoppingSession);

            double totalPrice = subtractProducts(list, shoppingSessionDto, total);
            shoppingSession.setTotal(totalPrice);
            shoppingSessionRepository.save(shoppingSession);

            OrderSession orderSession = new OrderSession(shoppingSession.getUser().getId(),
                    shoppingSessionDto.getId());
            orderSessionRepository.save(orderSession);

            rollBackOrder(shoppingSessionDto);

        }catch (RuntimeException e){
            throw new RuntimeException(e.getMessage());
        }

        return ResponseDto.buildResponse(true, AppCode.OK, AppMessage.OK, true);
    }


    @Override
    public ResponseDto<ShoppingSessionDto> getShoppingSession(Integer id) {
        try {
            Optional<ShoppingSession> optional = shoppingSessionRepository.findById(id);
            if(optional.isPresent()){
                ShoppingSessionDto shoppingSessionDto = optional.map(ShoppingSessionMapper::toDto).get();
                return ResponseDto.buildResponse(shoppingSessionDto, AppCode.OK, AppMessage.OK, true);
            }else {
                return ResponseDto.buildResponse(null, AppCode.NOT_FOUND, AppMessage.NOT_FOUND, false);
            }
        }catch (Exception e){
            log.error("Error while getting product by id !!!");
            return ResponseDto.buildResponse(null, AppCode.ERROR, AppMessage.ERROR, false);
        }
    }


    @Override
    public ResponseDto<Boolean> deleteShoppingSession(Integer id) {
        try {
            if(!shoppingSessionRepository.existsById(id))
                return ResponseDto.buildResponse(false, AppCode.NOT_FOUND, AppMessage.NOT_FOUND, false);

            shoppingSessionRepository.deleteById(id);
            return ResponseDto.buildResponse(true, AppCode.OK, AppMessage.OK, true);
        }catch (Exception e){
            log.error("Error while deleting shopping session by id !!!");
            return ResponseDto.buildResponse(false, AppCode.ERROR, AppMessage.ERROR, false);
        }
    }


    public void rollBackOrder(ShoppingSessionDto shoppingSessionDto){

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if(orderDetailsRepository.findByUserIdAndCreatedAtAfter(
                        shoppingSessionDto.getUser().getId(),
                        DateUtil.getCurrentDate()
                        ) == null
                ){
                    Optional<ShoppingSession> shoppingSession = shoppingSessionRepository.findById(shoppingSessionDto.getId());
                    shoppingSession.ifPresent(session -> session.getItems().forEach(item -> {
                        ProductDto dto = productService.getProduct(item.getProduct().getId()).getData();
                        dto.setAmount(dto.getAmount() + item.getAmount());
                        productService.updateProduct(dto);
                    }));
                }
            }
        };

        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.MINUTE, 2);

        Timer timer = new Timer();
        timer.schedule(timerTask, calendar.getTime());

    }

    protected ShoppingSession buildSession(){
        ShoppingSession shoppingSession = new ShoppingSession();
        shoppingSession.setCreatedAt(DateUtil.getCurrentDate());
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal()
                instanceof UserDto userDto){
            shoppingSession.setUser(UserCustomMapper.toEntityWithId(userDto));
        }else {
                throw new UserNotFoundException("Can not find user during shopping session");
        }
        return shoppingSession;
    }

    private double subtractProducts(List<CartItemDto> list, ShoppingSessionDto shoppingSessionDto, AtomicDouble total) {

        for (CartItemDto item : list) {
            ProductDto productDto = productService.getProduct(item.getProduct().getId()).getData();
            if(productDto.getAmount() < item.getAmount())
                throw new ProductAmountNotEnoughException(
                        String.format("Customer ordered %d %s, but left only %d",
                                item.getAmount(),
                                productDto.getName(),
                                productDto.getAmount()));

            productDto.setAmount(productDto.getAmount() - item.getAmount());
            productService.updateProduct(productDto);

            if (productDto.getDiscount() != null){
                double percent = productDto.getDiscount().getDiscountPercent();
                double price = productDto.getPrice();
                total.addAndGet((price - (price / percent)) * item.getAmount());
            }else {
                total.addAndGet(productDto.getPrice() * item.getAmount());
            }

            item.setShoppingSession(shoppingSessionDto);
            cartItemService.addCartItem(item);
        }

        return total.doubleValue();
    }


    private Integer hasUserOldSession() {
        try {
            if(SecurityContextHolder.getContext().getAuthentication().getPrincipal()
                    instanceof UserDto userDto){
                if(shoppingSessionRepository.findByUserId(userDto.getId()).isPresent())
                    return userDto.getId();
            }

        }catch (Exception e){
            log.error("Error while checking does user have old session");
        }
        return -1;
    }



    private void removeOldSession(Integer userId) {
        try {
            shoppingSessionRepository.deleteByUserId(userId);
        }catch (Exception e){
            log.error("Error while deleting user's old session");
        }
    }

}
