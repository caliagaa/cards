package com.k4zmow.cards.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST,  reason = "Card name already exists")
public class CardAlreadyExistException extends Exception {
    public CardAlreadyExistException(String msg) {
        super(msg);
    }
}
