package brand.shop.system.mapper;

import brand.shop.system.dto.CardTypeDto;
import brand.shop.system.model.CardType;

public class CardTypeMapper {

    static public CardTypeDto toDto(CardType cardType){
        if(cardType == null) return null;
        return new CardTypeDto(cardType.getId(), cardType.getName());
    }

    static public CardType toEntity(CardTypeDto cardTypeDto){
        if(cardTypeDto == null) return null;
        return new CardType(cardTypeDto.getId(), cardTypeDto.getName());
    }

}
