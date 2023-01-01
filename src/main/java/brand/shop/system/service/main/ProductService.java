package brand.shop.system.service.main;

import brand.shop.system.dto.ProductDto;
import brand.shop.system.dto.ResponseDto;

import java.util.List;

public interface ProductService {
    ResponseDto<ProductDto> addProduct(ProductDto productDto);

    ResponseDto<ProductDto> updateProduct(ProductDto productDto);

    ResponseDto<ProductDto> getProduct(Integer id);

    ResponseDto<Boolean> deleteProduct(Integer id);

    ResponseDto<Boolean> setDiscount(List<Integer> list, Integer discountId, Integer amountDay);

    void finishDiscount(Integer amountDay, Integer discountId);
}
