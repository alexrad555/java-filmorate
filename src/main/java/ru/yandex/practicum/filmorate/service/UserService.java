package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserBirthdayValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService extends AbstractService<User>{

    private final UserStorage userStorage;

    @Override
    public User create(User data) {
        if (data.getName() == null) {
            data.setName(data.getLogin());
        }
        validate(data);
        return userStorage.create(data);
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
        userStorage.deleteFriend(id,friendId);
    }

    public User addFriend(Long id, Long friendId) {
        return userStorage.addFriend(id, friendId);
    }

    public List<User> getAllFriends(Long id) {
        return userStorage.getAllFriends(id);
    }

    public List<User> getCommonFriends(Long id, Long otherId) {
        return userStorage.getCommonFriends(id, otherId);
    }

    @Override
    public void validate(User data) {
        LocalDate currentDate = LocalDate.now();
        if (data.getBirthday().isAfter(currentDate)) {
            throw new UserBirthdayValidationException("User birthday is invalid");
        }
    }
}
