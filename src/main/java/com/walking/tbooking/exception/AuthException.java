package com.walking.tbooking.exception;

public class AuthException extends RuntimeException {
    public AuthException() {
        super("Неверный лошин или пароль");
    }
}
