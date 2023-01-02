package brand.shop.system.mapper;

import brand.shop.system.dto.CityDto;
import brand.shop.system.model.City;

public class CityCustomMapper {

    public static CityDto toDto(City city) {
        return new CityDto(city.getId(), city.getName());
    }

    public static City toEntity(CityDto cityDto) {
        return new City(cityDto.getId(), cityDto.getName());
    }


}
