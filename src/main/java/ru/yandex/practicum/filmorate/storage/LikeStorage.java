package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface LikeStorage {
    List<Film> getPopular(Integer count);

    void addLike(Integer filmId, Integer userId);

    void deleteLike(Integer filmId, Integer userId);
}
