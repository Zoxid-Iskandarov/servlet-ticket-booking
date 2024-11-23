package com.walking.tbooking.exception;

public class AuthException extends RuntimeException {
    public AuthException() {
        super("Неверный логин или пароль");
    }
}
