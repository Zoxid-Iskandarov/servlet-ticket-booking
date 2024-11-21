package com.walking.tbooking.domain.users;

import java.util.Arrays;
import java.util.Optional;

public enum Role {
    USER, ADMIN;

    public static Optional<Role> fromString(String role) {
        return Arrays.stream(values())
                .filter(it -> it.name().equalsIgnoreCase(role))
                .findFirst();
    }
}
