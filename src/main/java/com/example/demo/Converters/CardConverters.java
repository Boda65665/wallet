package com.example.demo.Converters;

import com.example.demo.DTO.CardDTO;
import com.example.demo.Entiti.Card;

public class CardConverters {
    public  CardDTO  CardInCardDTO(Card card){
        CardDTO card_convert = new CardDTO();
        card_convert.setBalance(card.getBalance());
        card_convert.setNumber(card.getNumber());
        card_convert.setPayment_system(card.getPay_sys());
        return card_convert;
    }
    public Card CardDTOInCard(CardDTO card){
        Card card_convert = new Card();
        card_convert.setBalance(card.getBalance());
        card_convert.setNumber(card.getNumber());
        card_convert.setPay_sys(card.getPayment_system());
        return card_convert;
    }
}
