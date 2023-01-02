package brand.shop.system.mapper;

import brand.shop.system.dto.ShoppingSessionDto;
import brand.shop.system.model.ShoppingSession;

import static brand.shop.system.mapper.CartItemMapper.cartItemDtoToEntityList;
import static brand.shop.system.mapper.CartItemMapper.cartItemToDtoList;

public class ShoppingSessionMapper {

    static public ShoppingSessionDto toDto(ShoppingSession shoppingSession){
        return ShoppingSessionDto.builder()
                .id(shoppingSession.getId())
                .user(UserCustomMapper.toDtoWithId(shoppingSession.getUser()))
                .total(shoppingSession.getTotal())
                .items(cartItemToDtoList(shoppingSession.getItems()))
                .createdAt(shoppingSession.getCreatedAt())
                .modifiedAt(shoppingSession.getModifiedAt())
                .build();
    }

    static public ShoppingSession toEntity(ShoppingSessionDto shoppingSessionDto){
        return ShoppingSession.builder()
                .id(shoppingSessionDto.getId())
                .user(UserCustomMapper.toEntityWithId(shoppingSessionDto.getUser()))
                .total(shoppingSessionDto.getTotal())
                .items(cartItemDtoToEntityList(shoppingSessionDto.getItems()))
                .createdAt(shoppingSessionDto.getCreatedAt())
                .modifiedAt(shoppingSessionDto.getModifiedAt())
                .build();
    }

    static public ShoppingSessionDto toDtoWithId(ShoppingSession shoppingSession){
        return ShoppingSessionDto.builder()
                .id(shoppingSession.getId())
                .build();
    }

    static public ShoppingSession toEntityWithId(ShoppingSessionDto shoppingSessionDto){
        return ShoppingSession.builder()
                .id(shoppingSessionDto.getId())
                .build();
    }


}
