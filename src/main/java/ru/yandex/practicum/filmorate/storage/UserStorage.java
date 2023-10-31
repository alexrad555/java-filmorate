package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage extends AbstractStorage<User> {

    List<User> getAllFriends(Long count);

    User addFriend(Long id, Long userId);

    void deleteFriend(Long id, Long userId);

    List<User> getCommonFriends(Long id, Long otherId);
}
