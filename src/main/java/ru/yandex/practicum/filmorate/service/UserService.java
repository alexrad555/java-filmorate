package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService extends AbstractService<User> {

    private final UserStorage userStorage;

    @Override
    public void validate(User data) {
        LocalDate currentDate = LocalDate.now();
        if (data.getBirthday().isAfter(currentDate)) {
            throw new ValidationException("User birthday is invalid");
        }
    }

    @Override
    public User create(User data) {
        if (data.getName() == null || data.getName().isBlank()) {
            data.setName(data.getLogin());
        }
        validate(data);
        return userStorage.create(data);
    }

    @Override
    public User getById(Long id) {
        return userStorage.getById(id);
    }

    @Override
    public User update(User data) {
        validate(data);
        return userStorage.update(data);
    }

    @Override
    public List<User> getAll() {
        return userStorage.getAll();
    }

    public void deleteFriend(Long id, Long friendId) {
        User user = getUserFromStorage(id);
        User friendUser = getUserFromStorage(friendId);
        userStorage.deleteFriend(user, friendUser);
    }

    public User addFriend(Long id, Long friendId) {
        User user = getUserFromStorage(id);
        User friendUser = getUserFromStorage(friendId);
        return userStorage.addFriend(user, friendUser);
    }

    public List<User> getAllFriends(Long id) {
        User user = getUserFromStorage(id);
        return userStorage.getAllFriends(user);
    }

    public List<User> getCommonFriends(Long id, Long otherId) {
        User user = getUserFromStorage(id);
        User otherUser = getUserFromStorage(otherId);
        return userStorage.getCommonFriends(user, otherUser);
    }

    private User getUserFromStorage(Long  id) {
        Optional<User> userOptional = Optional.ofNullable(userStorage.getById(id));
        return userOptional.orElseThrow(() -> new DataNotFoundException("Не найден id"));
    }
}
