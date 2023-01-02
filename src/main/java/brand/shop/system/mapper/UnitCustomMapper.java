package brand.shop.system.mapper;

import brand.shop.system.dto.UnitDto;
import brand.shop.system.model.Unit;

public class UnitCustomMapper {

    static public UnitDto toDto(Unit unit){
        return UnitDto.builder()
                .id(unit.getId())
                .name(unit.getName())
                .shortName(unit.getShortName())
                .createdAt(unit.getCreatedAt())
                .modifiedAt(unit.getModifiedAt())
                .build();
    }


    static public Unit toEntity(UnitDto unit){
        return Unit.builder()
                .id(unit.getId())
                .name(unit.getName())
                .shortName(unit.getShortName())
                .createdAt(unit.getCreatedAt())
                .modifiedAt(unit.getModifiedAt())
                .build();
    }

}
