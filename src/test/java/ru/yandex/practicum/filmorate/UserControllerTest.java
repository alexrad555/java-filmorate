package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.time.LocalDate;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private UserService userService;

    @Test
    void validate() {
        User user = User.builder()
                .email("email@email.com")
                .login("login")
                .birthday(LocalDate.of(2000, 1, 1))
                .name("Name")
                .build();
        userService.validate(user);
    }

    @Test
    void validateNegative() {
        LocalDate currentDate = LocalDate.now().plusMonths(5);
        User user = User.builder()
                .email("email@email.com")
                .login("login")
                .birthday(currentDate)
                .name("Name")
                .build();
        Assertions.assertThrows(ValidationException.class, () -> userService.validate(user));
    }
}