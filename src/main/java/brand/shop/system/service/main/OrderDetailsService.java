package brand.shop.system.service.main;


import brand.shop.system.dto.OrderDetailsDto;
import brand.shop.system.dto.OrderItemsDto;
import brand.shop.system.dto.ResponseDto;

import java.util.List;

public interface OrderDetailsService {
    ResponseDto<OrderDetailsDto> addOrderDetails();

    ResponseDto<OrderDetailsDto> updateOrderDetails(OrderDetailsDto orderDetailsDto);

    ResponseDto<OrderDetailsDto> getOrderDetails(Integer id);

    ResponseDto<Boolean> deleteOrderDetails(Integer id);

    ResponseDto<List<OrderItemsDto>> findMostSoldProducts();
}
