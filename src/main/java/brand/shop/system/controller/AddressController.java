package brand.shop.system.controller;

import brand.shop.system.dto.AddressDto;
import brand.shop.system.dto.ResponseDto;
import brand.shop.system.service.main.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/add")
    public ResponseDto<AddressDto> addAddress(@RequestBody @Valid AddressDto addressDto){
        return addressService.addAddress(addressDto);
    }

    @PutMapping("/update")
    public ResponseDto<AddressDto> updateAddress(@RequestBody @Valid AddressDto addressDto){
        return addressService.updateAddress(addressDto);
    }

    @GetMapping("/get")
    public ResponseDto<AddressDto>  getAddress(@RequestParam Integer id){
        return addressService.getAddress(id);
    }

    @DeleteMapping("/delete")
    public ResponseDto<Boolean> deleteAddress(@RequestParam Integer id){
        return addressService.deleteAddress(id);
    }

}
