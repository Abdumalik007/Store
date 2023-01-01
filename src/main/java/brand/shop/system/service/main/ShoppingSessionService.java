package brand.shop.system.service.main;


import brand.shop.system.dto.CartItemDto;
import brand.shop.system.dto.ResponseDto;
import brand.shop.system.dto.ShoppingSessionDto;

import java.util.List;

public interface ShoppingSessionService{
    ResponseDto<Boolean> addShoppingSession(List<CartItemDto> list);

    ResponseDto<ShoppingSessionDto> getShoppingSession(Integer id);

    ResponseDto<Boolean> deleteShoppingSession(Integer id);
}
