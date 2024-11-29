package com.walking.tbooking.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class EncodingServiceTest {
    private final EncodingService encodingService = new EncodingService();

    @Test
    void encode_success() {
        var value = "password";
        var encoded = encodingService.encode(value);

        assertNotNull(encoded);
        assertNotEquals(encoded, value);
    }

    @Test
    void encode_exception_failed() {
        Executable executable = () -> encodingService.encode(null);

        assertThrows(NullPointerException.class, executable);
    }

    @ParameterizedTest
    @MethodSource("validAndInvalidMatchArguments")
    void match_success(String value, String encodedValue, boolean expectedResult) {
        var actual = encodingService.match(value, encodingService.encode(encodedValue));

        assertEquals(expectedResult, actual);
    }

    @Test
    void match_exception_failed() {
        assertThrows(NullPointerException.class, () -> encodingService.match(null, "password"));
        assertThrows(NullPointerException.class, () -> encodingService.match("value", null));
        assertThrows(NullPointerException.class, () -> encodingService.match(null, null));
    }

    static Stream<Arguments> validAndInvalidMatchArguments() {
        return Stream.of(
                Arguments.of("test", "test", true),
                Arguments.of("test1", "test", false),
                Arguments.of("password", "password", true),
                Arguments.of("t", "", false),
                Arguments.of("test1", "tes2", false),
                Arguments.of("test3", "test3", true)
        );
    }
}