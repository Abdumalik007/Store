package brand.shop.system.controller;

import brand.shop.system.dto.ResponseDto;
import brand.shop.system.dto.UnitDto;
import brand.shop.system.service.main.UnitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/unit")
@RequiredArgsConstructor
public class UnitController {

    private final UnitService unitService;

    @PostMapping("/add")
    public ResponseDto<UnitDto> addUnit(@RequestBody @Valid UnitDto unitDto){
        return unitService.addUnit(unitDto);
    }

    @PutMapping("/update")
    public ResponseDto<UnitDto> updateUnit(@RequestBody @Valid UnitDto unitDto){
        return unitService.updateUnit(unitDto);
    }

    @GetMapping("/get")
    public ResponseDto<UnitDto> getUnit(@RequestParam Integer id){
        return unitService.getUnit(id);
    }

    @DeleteMapping("/delete")
    public ResponseDto<Boolean> deleteUnit(@RequestParam Integer id){
        return unitService.deleteUnit(id);
    }

}
