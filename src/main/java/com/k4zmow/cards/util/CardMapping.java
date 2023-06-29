package com.k4zmow.cards.util;

import com.k4zmow.cards.controller.request.CardRequest;
import com.k4zmow.cards.model.Card;

public class CardMapping {

    public static Card parseCardRequest(CardRequest cardRequest){
        Card card = Card.builder()
                .name(cardRequest.getName())
                .description(cardRequest.getDescription())
                .color(cardRequest.getColor())
                .build();
        return card;
    }
}
