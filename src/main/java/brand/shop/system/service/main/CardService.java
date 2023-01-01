package brand.shop.system.service.main;

import brand.shop.system.dto.CardDto;
import brand.shop.system.dto.ResponseDto;

public interface CardService {

    ResponseDto<CardDto> addCard(CardDto cardDto);

    ResponseDto<CardDto> updateCard(CardDto cardDto);

    ResponseDto<CardDto> getCard(Integer id);

    ResponseDto<Boolean> deleteCard(Integer id);

    CardDto getByUserId(Integer userId);
}
