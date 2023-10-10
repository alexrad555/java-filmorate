package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.UserBirthdayValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController extends BaseController<User> {

    @GetMapping
    public List<User> getAll() {
        log.info("Getting all users");
        return super.getAll();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.info("Creating user {}", user);
        return super.create(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.info("Update user {}", user);
        return super.update(user);
    }

    @Override
    public void validate(User data) {
        LocalDate currentDate = LocalDate.now();
        if (data.getBirthday().isAfter(currentDate)) {
            throw new UserBirthdayValidationException("User birthday is invalid");
        }

    }
}
