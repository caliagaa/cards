package com.k4zmow.cards.model;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonInclude;

@Data
public class JWTAuthResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String accessToken;
}
