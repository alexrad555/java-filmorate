package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenreStorage {

    List<Genre> getAll();

    Genre getById(Integer id);

    List<Genre> getFilmGenres(Integer filmId);

    void addFilmGenres(Integer filmId, List<Genre> genres);

    void deleteFilmGenres(Integer filmId);
}
