package com.k4zmow.cards.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SearchCriteria {
    private String key;
    private String operation;
    private Object value;
}
