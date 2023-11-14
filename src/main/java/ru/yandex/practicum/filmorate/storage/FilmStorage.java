package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FilmStorage {

    List<Film> getPopular();

    Film addLike(Film film, User user);

    void deleteLike(Film film, User user);

    Film create(Film data);

    Film update(Film data);

    List<Film> getAll();

    Film getById(Long id);
}
