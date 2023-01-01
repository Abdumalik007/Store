package brand.shop.system.service.main;

import brand.shop.system.dto.ResponseDto;
import brand.shop.system.dto.UnitDto;

public interface UnitService {
    ResponseDto<UnitDto> addUnit(UnitDto unitDto);

    ResponseDto<UnitDto> updateUnit(UnitDto unitDto);

    ResponseDto<UnitDto> getUnit(Integer id);

    ResponseDto<Boolean> deleteUnit(Integer id);
}
