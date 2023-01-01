package brand.shop.system.service.main;

import brand.shop.system.dto.AddressDto;
import brand.shop.system.dto.ResponseDto;

public interface AddressService {
    ResponseDto<AddressDto> addAddress(AddressDto addressDto);

    ResponseDto<AddressDto> updateAddress(AddressDto addressDto);

    ResponseDto<AddressDto> getAddress(Integer id);

    ResponseDto<Boolean> deleteAddress(Integer id);
}
