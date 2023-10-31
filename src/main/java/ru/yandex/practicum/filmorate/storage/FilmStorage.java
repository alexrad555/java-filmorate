package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Map;

public interface FilmStorage extends AbstractStorage<Film> {

    List<Film> getPopular();

    Film addLike(Long id, Long userId);

    void deleteLike(Long id, Long userId);
}
