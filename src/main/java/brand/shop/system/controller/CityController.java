package brand.shop.system.controller;

import brand.shop.system.dto.CityDto;
import brand.shop.system.dto.ResponseDto;
import brand.shop.system.service.main.CityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/city")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @PostMapping("/add")
    public ResponseDto<CityDto> addCity(@RequestBody @Valid CityDto cityDto){
        return cityService.addCity(cityDto);
    }

    @PutMapping("/update")
    public ResponseDto<CityDto> updateCity(@RequestBody @Valid CityDto cityDto){
        return cityService.updateCity(cityDto);
    }

    @GetMapping("/get")
    public ResponseDto<CityDto> getCity(@RequestParam @Valid Integer id){
        return cityService.getCity(id);
    }

    @DeleteMapping("/delete")
    public ResponseDto<Boolean> deleteCity(@RequestParam @Valid Integer id){
        return cityService.deleteCity(id);
    }

}
