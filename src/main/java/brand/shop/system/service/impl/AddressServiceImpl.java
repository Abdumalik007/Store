package brand.shop.system.service.impl;

import brand.shop.system.dto.AddressDto;
import brand.shop.system.dto.ResponseDto;
import brand.shop.system.helper.AppCode;
import brand.shop.system.helper.AppMessage;
import brand.shop.system.helper.DateUtil;
import brand.shop.system.mapper.AddressCustomMapper;
import brand.shop.system.model.Address;
import brand.shop.system.repository.AddressRepository;
import brand.shop.system.service.main.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    public ResponseDto<AddressDto> addAddress(AddressDto addressDto) {
        try {
            addressDto.setCreatedAt(DateUtil.getCurrentDate());
            Address address = AddressCustomMapper.toEntity(addressDto);

            addressRepository.save(address);
            addressDto.setId(address.getId());

            return ResponseDto.buildResponse(addressDto, AppCode.OK, AppMessage.OK, true);
        }catch (Exception e){
            log.error("Error while saving address into database !!!");
            return ResponseDto.buildResponse(null, AppCode.ERROR, AppMessage.ERROR, false);
        }
    }

    @Override
    public ResponseDto<AddressDto> updateAddress(AddressDto addressDto) {
        if(!addressRepository.existsById(addressDto.getId()))
            return ResponseDto.buildResponse(null, AppCode.NOT_FOUND, AppMessage.NOT_FOUND, false);
        try {
            addressDto.setModifiedAt(DateUtil.getCurrentDate());
            Address address = AddressCustomMapper.toEntity(addressDto);
            addressRepository.save(address);
            return ResponseDto.buildResponse(addressDto, AppCode.OK, AppMessage.OK, true);
        }catch (Exception e){
            log.error("Error while updating address !!!");
            return ResponseDto.buildResponse(null, AppCode.ERROR, AppMessage.ERROR, false);
        }
    }

    @Override
    public ResponseDto<AddressDto> getAddress(Integer id) {
        try {
            Optional<Address> optional = addressRepository.findById(id);
            if(optional.isPresent()){
                AddressDto addressDto = optional.map(AddressCustomMapper::toDto).get();
                return ResponseDto.buildResponse(addressDto, AppCode.OK, AppMessage.OK, true);
            }else {
                return ResponseDto.buildResponse(null, AppCode.NOT_FOUND, AppMessage.NOT_FOUND, false);
            }
        }catch (Exception e){
            log.error("Error while getting address by id !!!");
            return ResponseDto.buildResponse(null, AppCode.ERROR, AppMessage.ERROR, false);
        }
    }

    @Override
    public ResponseDto<Boolean> deleteAddress(Integer id) {
        try {
            if(!addressRepository.existsById(id))
                return ResponseDto.buildResponse(false, AppCode.NOT_FOUND, AppMessage.NOT_FOUND, false);

            addressRepository.deleteById(id);
            return ResponseDto.buildResponse(true, AppCode.OK, AppMessage.OK, true);
        }catch (Exception e){
            log.error("Error while deleting address by id !!!");
            return ResponseDto.buildResponse(false, AppCode.ERROR, AppMessage.ERROR, false);
        }
    }
}
