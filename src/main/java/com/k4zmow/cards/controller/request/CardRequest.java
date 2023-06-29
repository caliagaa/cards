package com.k4zmow.cards.controller.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CardRequest {
    private String name;
    private String description;
    private String color;
}
