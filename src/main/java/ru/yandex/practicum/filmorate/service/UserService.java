package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService extends AbstractService<User> {

    private final UserStorage userStorage;

    private final FriendStorage friendStorage;

    @Autowired
    public UserService(UserStorage userStorage,
                        FriendStorage friendStorage) {
        this.userStorage = userStorage;
        this.friendStorage = friendStorage;
    }

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
    public User getById(Integer id) {
        return getUserFromStorage(id);
    }

    @Override
    public User update(User user) {
        User userCheck = getUserFromStorage(user.getId());
        validate(user);
        return userStorage.update(user);
    }

    @Override
    public List<User> getAll() {
        return userStorage.getAll();
    }

    public void deleteFriend(Integer id, Integer friendId) {
        User userCheck = getUserFromStorage(id);
        User friendUserCheck = getUserFromStorage(friendId);
        friendStorage.deleteFriend(id, friendId);
    }

    public User addFriend(Integer userId, Integer friendIs) {
        User userCheck = getUserFromStorage(userId);
        User friendUserCheck = getUserFromStorage(friendIs);
        friendStorage.addFriend(userId, friendIs);
        return userCheck;
    }

    public List<User> getAllFriends(Integer userId) {
        User user = getUserFromStorage(userId);
        return friendStorage.getAllFriends(userId);
    }

    public List<User> getCommonFriends(Integer userId, Integer otherId) {
        User user = getUserFromStorage(userId);
        User otherUser = getUserFromStorage(otherId);
        return friendStorage.getCommonFriends(userId, otherId);
    }

    private User getUserFromStorage(Integer  id) {
        Optional<User> userOptional = Optional.ofNullable(userStorage.getById(id));
        return userOptional.orElseThrow(() -> new DataNotFoundException("Не найден id"));
    }
}
