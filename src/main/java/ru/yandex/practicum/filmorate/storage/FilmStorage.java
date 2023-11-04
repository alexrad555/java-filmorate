package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FilmStorage extends AbstractStorage<Film> {

    List<Film> getPopular();

    Film addLike(Film film, User user);

    void deleteLike(Film film, User user);
}
