package brand.shop.system.service.main;


import brand.shop.system.dto.CartItemDto;
import brand.shop.system.dto.ResponseDto;

import java.util.List;

public interface CartItemService {

    void addCartItem(CartItemDto cartItemDto);


    CartItemDto getCartItem(Integer id);

    ResponseDto<Boolean> deleteCartItem(Integer id);

    void deleteBySessionId(Integer sessionId);

    List<CartItemDto> getBySessionId(Integer sessionId);
}
