package brand.shop.system.service.impl;

import brand.shop.system.dto.OrderItemsDto;
import brand.shop.system.helper.DateUtil;
import brand.shop.system.mapper.OrderItemMapper;
import brand.shop.system.model.OrderItems;
import brand.shop.system.repository.OrderItemsRepository;
import brand.shop.system.service.main.OrderItemsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderItemsServiceImpl implements OrderItemsService {

    private final OrderItemsRepository orderItemsRepository;

    @Override
    public void addOrderItems(OrderItemsDto orderitemsDto) {
        try {
            orderitemsDto.setCreatedAt(DateUtil.getCurrentDate());
            OrderItems orderItems = OrderItemMapper.toEntity(orderitemsDto);
            orderItemsRepository.save(orderItems);
        }catch (Exception e){
            log.error("Error while saving order items into database !!!");
        }
    }


    @Override
    public void deleteByOrderDetailsId(Integer orderDetailsId) {
        try {
            orderItemsRepository.deleteByOrderDetailsId(orderDetailsId);
        }catch (Exception e){
            log.error("Error while deleting order items by order details id id !!!");
        }
    }

    @Override
    public List<OrderItemsDto> findMostSoldProducts() {
        return orderItemsRepository.findByOrderByAmountAsc()
                .stream().map(OrderItemMapper::toDto).toList();
    }


}
