package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.UserBirthdayValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserControllerTest {
    private UserController userController;

    @BeforeEach
    void setUp() {
        userController = new UserController();
    }

    @Test
    void validate() {
        User user = User.builder()
                .email("email@email.com")
                .login("login")
                .birthday(LocalDate.of(2000, 1, 1))
                .name("Name")
                .build();
        userController.validate(user);
    }

    @Test
    void validateNegative() {
        User user = User.builder()
                .email("email@email.com")
                .login("login")
                .birthday(LocalDate.of(2023, 11, 3))
                .name("Name")
                .build();
        Assertions.assertThrows(UserBirthdayValidationException.class, () -> userController.validate(user));
    }
}