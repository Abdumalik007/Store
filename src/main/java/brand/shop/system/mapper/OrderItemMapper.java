package brand.shop.system.mapper;

import brand.shop.system.dto.OrderItemsDto;
import brand.shop.system.model.OrderItems;

import java.util.ArrayList;
import java.util.List;

public class OrderItemMapper {

    static public OrderItemsDto toDto(OrderItems cartItem){
        return OrderItemsDto.builder()
                .id(cartItem.getId())
                .product(ProductCustomMapper.toDtoWithId(cartItem.getProduct()))
                .orderDetails(OrderDetailsMapper.toDtoWithId(cartItem.getOrderDetails()))
                .amount(cartItem.getAmount())
                .createdAt(cartItem.getCreatedAt())
                .build();
    }


    static public OrderItems toEntity(OrderItemsDto cartItemDto){
        return OrderItems.builder()
                .id(cartItemDto.getId())
                .product(ProductCustomMapper.toEntityWithId(cartItemDto.getProduct()))
                .orderDetails(OrderDetailsMapper.toEntity(cartItemDto.getOrderDetails()))
                .amount(cartItemDto.getAmount())
                .createdAt(cartItemDto.getCreatedAt())
                .build();
    }


    public static List<OrderItemsDto> orderItemToDtoList(List<OrderItems> list){
        if(list == null) return null;
        List<OrderItemsDto> dtoList = new ArrayList<>();

        for (OrderItems c : list) {
            dtoList.add(toDto(c));
        }
        return dtoList;
    }


    public static List<OrderItems> orderItemDtoToEntityList(List<OrderItemsDto> listDto){
        if(listDto == null) return null;
        List<OrderItems> list = new ArrayList<>();

        for (OrderItemsDto c : listDto){
            list.add(toEntity(c));
        }

        return list;
    }

}
