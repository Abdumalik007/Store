package brand.shop.system.mapper;

import brand.shop.system.dto.OrderDetailsDto;
import brand.shop.system.model.OrderDetails;

import static brand.shop.system.mapper.OrderItemMapper.orderItemDtoToEntityList;
import static brand.shop.system.mapper.OrderItemMapper.orderItemToDtoList;

public class OrderDetailsMapper {

    static public OrderDetailsDto toDto(OrderDetails orderDetails){
        return OrderDetailsDto.builder()
                .id(orderDetails.getId())
                .user(UserCustomMapper.toDtoWithId(orderDetails.getUser()))
                .total(orderDetails.getTotal())
                .orderItemsList(orderItemToDtoList(orderDetails.getOrderItemsList()))
                .createdAt(orderDetails.getCreatedAt())
                .build();
    }

    static public OrderDetails toEntity(OrderDetailsDto orderDetailsDto){
        return OrderDetails.builder()
                .id(orderDetailsDto.getId())
                .user(UserCustomMapper.toEntityWithId(orderDetailsDto.getUser()))
                .total(orderDetailsDto.getTotal())
                .orderItemsList(orderItemDtoToEntityList(orderDetailsDto.getOrderItemsList()))
                .createdAt(orderDetailsDto.getCreatedAt())
                .build();
    }

    static public OrderDetailsDto toDtoWithId(OrderDetails orderDetails){
        return OrderDetailsDto.builder()
                .id(orderDetails.getId())
                .build();
    }

    static public OrderDetails toEntityWithId(OrderDetailsDto orderDetailsDto){
        return OrderDetails.builder()
                .id(orderDetailsDto.getId())
                .build();
    }
}
