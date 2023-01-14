package brand.shop.system.controller;


import brand.shop.system.dto.CartItemDto;
import brand.shop.system.dto.ResponseDto;
import brand.shop.system.dto.ShoppingSessionDto;
import brand.shop.system.service.main.ShoppingSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shop-session")
@RequiredArgsConstructor
public class ShoppingSessionController{

    private final ShoppingSessionService shoppingSessionService;

    @PostMapping("/add")
    public ResponseDto<Boolean> addShoppingSession(@RequestBody List<CartItemDto> list){
        return shoppingSessionService.addShoppingSession(list);
    }

    @GetMapping("/get")
    public ResponseDto<ShoppingSessionDto> getShoppingSession(@RequestParam Integer id){
        return shoppingSessionService.getShoppingSession(id);
    }

    @DeleteMapping("/delete")
    public ResponseDto<Boolean> deleteShoppingSession(@RequestParam Integer id){
        return shoppingSessionService.deleteShoppingSession(id);
    }

}
