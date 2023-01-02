package brand.shop.system.mapper;

import brand.shop.system.dto.PaymentDetailsDto;
import brand.shop.system.model.PaymentDetails;

public class PaymentDetailsMapper {

    static public PaymentDetailsDto toDto(PaymentDetails paymentDetails){
        return PaymentDetailsDto.builder()
                .id(paymentDetails.getId())
                .status(paymentDetails.getStatus())
                .amount(paymentDetails.getAmount())
                .orderDetails(OrderDetailsMapper.toDtoWithId(paymentDetails.getOrderDetails()))
                .createdAt(paymentDetails.getCreatedAt())
                .build();
    }


    static public PaymentDetails toEntity(PaymentDetailsDto paymentDetailsDto){
        return PaymentDetails.builder()
                .id(paymentDetailsDto.getId())
                .status(paymentDetailsDto.getStatus())
                .orderDetails(OrderDetailsMapper.toEntityWithId(paymentDetailsDto.getOrderDetails()))
                .amount(paymentDetailsDto.getAmount())
                .createdAt(paymentDetailsDto.getCreatedAt())
                .build();
    }

}
