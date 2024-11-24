package com.walking.tbooking.domain.ticket;

import java.util.Arrays;
import java.util.Optional;

public enum ServiceClass {
    ECONOMY,
    BUSINESS,
    FIRST;

    public static Optional<ServiceClass> fromString(String serviceClass) {
        return Arrays.stream(values())
                .filter(it -> it.name().equalsIgnoreCase(serviceClass))
                .findFirst();
    }
}
