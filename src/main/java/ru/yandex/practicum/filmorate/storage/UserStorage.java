package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    User create(User data);

    User update(User data);

    List<User> getAll();

    User getById(int id);

}
