package brand.shop.system.service.main;


import brand.shop.system.dto.OrderItemsDto;

import java.util.List;

public interface OrderItemsService{
    void addOrderItems(OrderItemsDto orderitemsDto);

    void deleteByOrderDetailsId(Integer orderDetailsId);

    List<OrderItemsDto> findMostSoldProducts();
}
