package ru.yandex.practicum.filmorate.controller.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.exception.FilmorateValidationException;
import ru.yandex.practicum.filmorate.exception.InternalServerErrorException;
import ru.yandex.practicum.filmorate.exception.UserBirthdayValidationException;

import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler()
    public ErrorResponse handlerDataNotFoundException(final DataNotFoundException exception) {
        log.error("Данные не найдены {}", exception.getMessage());
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler({FilmorateValidationException.class, UserBirthdayValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handlerIncorrectCount(final RuntimeException exception) {
        log.error("Ошибка входящих данных {}", exception.getMessage());
        return Map.of(
                "error", "Ошибка входящих данных",
                "errorMessage", exception.getMessage()
        );
    }

    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handlerInternalServerErrorException(final InternalServerErrorException exception) {
        log.error("Ошибка сервера {}", exception.getMessage());
        return new ErrorResponse(exception.getMessage());
    }
}
