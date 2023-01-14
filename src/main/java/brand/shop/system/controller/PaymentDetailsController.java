package brand.shop.system.controller;


import brand.shop.system.dto.PaymentDetailsDto;
import brand.shop.system.dto.ResponseDto;
import brand.shop.system.service.main.PaymentDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentDetailsController {

    private final PaymentDetailsService paymentDetailsService;

    @GetMapping("/get")
    public ResponseDto<PaymentDetailsDto> getPaymentDetails(@RequestParam Integer id){
        return paymentDetailsService.getPaymentDetails(id);
    }

    @DeleteMapping("/delete")
    public ResponseDto<Boolean> deletePaymentDetails(@RequestParam Integer id){
        return paymentDetailsService.deletePaymentDetails(id);
    }

}
