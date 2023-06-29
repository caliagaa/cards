package com.k4zmow.cards.util;

import com.k4zmow.cards.model.Status;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Status, String> {

    @Override
    public String convertToDatabaseColumn(Status status) {
        return status.getName();
    }

    @Override
    public Status convertToEntityAttribute(String s) {
        return Status.fromString(s);
    }
}
