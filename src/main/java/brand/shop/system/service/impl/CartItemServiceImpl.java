package brand.shop.system.service.impl;

import brand.shop.system.dto.CartItemDto;
import brand.shop.system.dto.ResponseDto;
import brand.shop.system.helper.AppCode;
import brand.shop.system.helper.AppMessage;
import brand.shop.system.helper.DateUtil;
import brand.shop.system.mapper.CartItemMapper;
import brand.shop.system.model.CartItem;
import brand.shop.system.repository.CartItemRepository;
import brand.shop.system.service.main.CartItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;

    @Override
    public void addCartItem(CartItemDto cartItemDto) {
        try {
            CartItem cartItem = CartItemMapper.toEntity(cartItemDto);
            cartItem.setCreatedAt(DateUtil.getCurrentDate());
            cartItemRepository.save(cartItem);

        }catch (Exception e){
            log.error("Error while saving card into database !!!");
        }
    }


    @Override
    public CartItemDto getCartItem(Integer id) {
        try {
            Optional<CartItem> optional = cartItemRepository.findById(id);
            return optional.map(CartItemMapper::toDto).orElse(null);
        }catch (Exception e){
            log.error("Error while deleting cart item by id !!!");
        }
        return null;
    }

    @Override
    public ResponseDto<Boolean> deleteCartItem(Integer id) {
        try {
            cartItemRepository.deleteById(id);
            return ResponseDto.buildResponse(true, AppCode.OK, AppMessage.OK, true);
        }catch (Exception e){
            log.error("Error while deleting cart item by id !!!");
            return ResponseDto.buildResponse(false, AppCode.ERROR, AppMessage.ERROR, false);
        }
    }

    @Override
    public void deleteBySessionId(Integer sessionId) {
        try {
            cartItemRepository.deleteByShoppingSessionId(sessionId);
        }catch (Exception e){
            log.error("Error while deleting cart item by session id !!!");
        }
    }

    @Override
    public List<CartItemDto> getBySessionId(Integer sessionId) {
        return cartItemRepository.findAllByShoppingSessionId(sessionId)
                .stream().map(CartItemMapper::toDto).collect(Collectors.toList());
    }


}
