package com.k4zmow.cards.util;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Roles {
    ADMIN("Admin"),
    MEMBER("Member");

    private final String name;

    Roles(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    public static Roles fromString(String name) {
        for (Roles b : Roles.values()) {
            if (b.getName().equalsIgnoreCase(name)) {
                return b;
            }
        }
        return null;
    }
}
