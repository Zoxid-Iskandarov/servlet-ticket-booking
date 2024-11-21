package com.walking.tbooking.exception;

public class DuplicateUserException extends RuntimeException {
    public DuplicateUserException() {
        super("Пользователь с таким именем уже существует");
    }
}
