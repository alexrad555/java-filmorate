package ru.yandex.practicum.filmorate.exception;

public class UserBirthdayValidationException extends RuntimeException {
    public UserBirthdayValidationException(String message) {
        super(message);
    }
}
