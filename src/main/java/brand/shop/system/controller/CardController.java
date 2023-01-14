package brand.shop.system.controller;

import brand.shop.system.dto.CardDto;
import brand.shop.system.dto.ResponseDto;
import brand.shop.system.service.main.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/card")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping("/add")
    public ResponseDto<CardDto> addCard(@RequestBody @Valid CardDto cardDto){
        return cardService.addCard(cardDto);
    }

    @PutMapping("/update")
    public ResponseDto<CardDto> updateCard(@RequestBody @Valid CardDto cardDto){
        return cardService.updateCard(cardDto);
    }

    @GetMapping("/get")
    public ResponseDto<CardDto> getCard(@RequestParam Integer id){
        return cardService.getCard(id);
    }

    @DeleteMapping("/delete")
    public ResponseDto<Boolean> deleteCard(@RequestParam @Valid Integer id){
        return cardService.deleteCard(id);
    }

}
