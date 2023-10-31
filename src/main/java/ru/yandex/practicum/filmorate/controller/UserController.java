package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<User> getAll() {
        log.info("Getting all users");
        return userService.getAll();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.info("Creating user {}", user);
        return userService.create(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.info("Update user {}", user);
        return userService.update(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriends(
            @PathVariable Long id,
            @PathVariable Long friendId) {
        log.info("Add friend id {}", id);
        return userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void delete(
            @PathVariable Long id,
            @PathVariable Long friendId) {
        log.info("Delete friendId {}", friendId);
        userService.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getAllFriends(@PathVariable Long id) {
        log.info("Getting all friends");
        return userService.getAllFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getAllFriends(
            @PathVariable Long id,
            @PathVariable Long otherId) {
        log.info("Getting common friends");
        return userService.getCommonFriends(id, otherId);
    }
}
