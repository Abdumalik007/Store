package brand.shop.system.service.main;

import brand.shop.system.dto.CityDto;
import brand.shop.system.dto.ResponseDto;

public interface CityService {
    ResponseDto<CityDto> addCity(CityDto cityDto);

    ResponseDto<CityDto> updateCity(CityDto cityDto);

    ResponseDto<CityDto> getCity(Integer id);

    ResponseDto<Boolean> deleteCity(Integer id);
}
