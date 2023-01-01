package brand.shop.system.service.main;

import brand.shop.system.dto.DiscountDto;
import brand.shop.system.dto.ResponseDto;

public interface DiscountService {
    ResponseDto<DiscountDto> addDiscount(DiscountDto discountDto);

    ResponseDto<DiscountDto> updateDiscount(DiscountDto discountDto);

    ResponseDto<DiscountDto> getDiscount(Integer id);

    ResponseDto<Boolean> deleteDiscount(Integer id);

}
