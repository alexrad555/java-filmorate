package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    User create(User data);

    User update(User data);

    List<User> getAll();

    User getById(Long id);

    List<User> getAllFriends(User user);

    User addFriend(User user, User friendUser);

    void deleteFriend(User user, User friendUser);

    List<User> getCommonFriends(User user, User otherUser);
}
