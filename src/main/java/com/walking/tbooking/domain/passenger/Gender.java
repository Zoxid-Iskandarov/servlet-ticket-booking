package com.walking.tbooking.domain.passenger;

import java.util.Arrays;
import java.util.Optional;

public enum Gender {
    MALE, FEMALE;

    public static Optional<Gender> fromString(String gender) {
        return Arrays.stream(values())
                .filter(it -> it.name().equalsIgnoreCase(gender))
                .findFirst();
    }
}
