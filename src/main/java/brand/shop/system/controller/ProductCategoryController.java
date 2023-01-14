package brand.shop.system.controller;

import brand.shop.system.dto.ProductCategoryDto;
import brand.shop.system.dto.ResponseDto;
import brand.shop.system.service.main.ProductCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/p-category")
@RequiredArgsConstructor
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    @PostMapping("/add")
    public ResponseDto<ProductCategoryDto> addProductCategory(@RequestBody @Valid ProductCategoryDto proCatDto){
        return productCategoryService.addProductCategory(proCatDto);
    }

    @PutMapping("/update")
    public ResponseDto<ProductCategoryDto> updateProductCategory(@RequestBody @Valid  ProductCategoryDto proCatDto){
        return productCategoryService.updateProductCategory(proCatDto);
    }

    @PutMapping("/set-discount")
    public ResponseDto<Boolean> discountCategory(@RequestParam Integer disId,
                                                 @RequestParam Integer categoryId,
                                                 @RequestParam Integer day){
        return productCategoryService.discountCategory(disId, categoryId, day);
    }

    @GetMapping("/get")
    public ResponseDto<ProductCategoryDto> getProductCategory(@RequestParam Integer id){
        return productCategoryService.getProductCategory(id);
    }

    @DeleteMapping("/delete")
    public ResponseDto<Boolean> deleteProductCategory(@RequestParam Integer id){
        return productCategoryService.deleteProductCategory(id);
    }

}
