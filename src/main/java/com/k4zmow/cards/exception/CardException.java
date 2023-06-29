package com.k4zmow.cards.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CardException extends Exception {
    public CardException(String msg) {
        super(msg);
    }
}
