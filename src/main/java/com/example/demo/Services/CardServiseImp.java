//package com.example.demo.Services;
//
//import com.example.demo.Converters.CardConverters;
//import com.example.demo.Converters.UserConverters;
//import com.example.demo.DTO.CardDTO;
//import com.example.demo.DTO.UserDTO;
//import com.example.demo.Entiti.Card;
//import com.example.demo.Repositories.CardRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//@Service
//public class CardServiseImp implements CardServise{
//    @Autowired
//    CardRepository cardRepository;
//
//    CardConverters cardConverters = new CardConverters();
//    UserConverters userConverters = new UserConverters();
//
//    @Override
//    public void cardSave(CardDTO cardDTO) {
//        Card card = cardConverters.CardDTOInCard(cardDTO);
//        card.setUser(userConverters.FromUserDTOinUsers(cardDTO.getUserDTO()));
//        cardRepository.save(card);
//    }
//
//    @Override
//    public void delete(int id) {
//        cardRepository.deleteById(id);
//    }
//
//    @Override
//    public List<CardDTO> findByAll() {
//        List<Card> cards= cardRepository.findAll();
//        List<CardDTO> CardDTOs = new ArrayList<CardDTO>();
//
//        for(int i =0;i<cards.size();i++){
//
//            Card card = cards.get(i);
//            CardDTO cardDTO = cardConverters.CardInCardDTO(card);
//            cardDTO.setUserDTO(userConverters.FromUsersInUserDTO(card.getUser()));
//            CardDTOs.add(cardDTO);
//    }
//        return CardDTOs;
//    }
//
//    @Override
//    public CardDTO findById(int id) {
//        Card card = cardRepository.findById(id);
//        CardDTO cardDTO = cardConverters.CardInCardDTO(card);
//        cardDTO.setUserDTO(userConverters.FromUsersInUserDTO(card.getUser()));
//        return cardDTO;
//    }
//
//    @Override
//    public CardDTO findByNumber(String number) {
//        Card card = cardRepository.findByNumber(number);
//        CardDTO cardDTO = cardConverters.CardInCardDTO(card);
//        cardDTO.setUserDTO(userConverters.FromUsersInUserDTO(card.getUser()));
//        return cardDTO;
//    }
//
//    @Override
//    public CardDTO findByUser(UserDTO userDTO) {
//        try {
//            Card card = cardRepository.findByUser(userConverters.FromUserDTOinUsers(userDTO));
//            CardDTO cardDTO = cardConverters.CardInCardDTO(card);
//            cardDTO.setUserDTO(userDTO);
//            return cardDTO;
//        }
//        catch (NullPointerException err) {
//            return null;
//        }
//    }
//}
