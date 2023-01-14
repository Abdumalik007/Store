package brand.shop.system.controller;


import brand.shop.system.dto.OrderDetailsDto;
import brand.shop.system.dto.OrderItemsDto;
import brand.shop.system.dto.ResponseDto;
import brand.shop.system.service.main.OrderDetailsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderDetailsController {

    private final OrderDetailsService orderDetailsService;

    @PostMapping("/add")
    public ResponseDto<OrderDetailsDto> addOrderDetails(){
        return orderDetailsService.addOrderDetails();
    }

    @PutMapping("/update")
    public ResponseDto<OrderDetailsDto> updateOrderDetails(@RequestBody @Valid OrderDetailsDto orderDetailsDto){
        return orderDetailsService.updateOrderDetails(orderDetailsDto);
    }

    @GetMapping("/get")
    public ResponseDto<OrderDetailsDto> getOrderDetails(@RequestParam Integer id){
        return orderDetailsService.getOrderDetails(id);
    }

    @DeleteMapping("/delete")
    public ResponseDto<Boolean> deleteOrderDetails(@RequestParam Integer id){
        return orderDetailsService.deleteOrderDetails(id);
    }

    @GetMapping("/sort")
    public ResponseDto<List<OrderItemsDto>> getMostSoldProducts(){
        return orderDetailsService.findMostSoldProducts();
    }


}
