package brand.shop.system.service.main;


import brand.shop.system.dto.PaymentDetailsDto;
import brand.shop.system.dto.ResponseDto;

public interface PaymentDetailsService {
    ResponseDto<PaymentDetailsDto> addPaymentDetails(PaymentDetailsDto paymentDetailsDto);

    ResponseDto<PaymentDetailsDto> updatePaymentDetails(PaymentDetailsDto paymentDetailsDto);

    ResponseDto<PaymentDetailsDto> getPaymentDetails(Integer id);

    ResponseDto<Boolean> deletePaymentDetails(Integer id);

    void deleteByOrderId(Integer id);
}
