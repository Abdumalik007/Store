package brand.shop.system.service.main;

import brand.shop.system.dto.ProductCategoryDto;
import brand.shop.system.dto.ResponseDto;

public interface ProductCategoryService {
    ResponseDto<ProductCategoryDto> addProductCategory(ProductCategoryDto proCatDto);

    ResponseDto<ProductCategoryDto> updateProductCategory(ProductCategoryDto proCatDto);

    ResponseDto<ProductCategoryDto> getProductCategory(Integer id);

    ResponseDto<Boolean> deleteProductCategory(Integer id);

    ResponseDto<Boolean> discountCategory(Integer discountId, Integer productCategoryId, Integer day);
}
