package com.k4zmow.cards.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {
    TO_DO("To Do"),
    IN_PROGRESS("In Progress"),
    DONE("Done");

    private final String name;

    Status(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    public static Status fromString(String name) {
        for (Status b : Status.values()) {
            if (b.getName().equalsIgnoreCase(name)) {
                return b;
            }
        }
        return null;
    }
}
