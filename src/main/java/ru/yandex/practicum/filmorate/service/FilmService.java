package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.*;

import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
public class FilmService extends AbstractService<Film> {

    private final FilmStorage filmStorage;
    private final LikeStorage likeStorage;
    private final GenreStorage genreStorage;
    private final MpaStorage mpaStorage;
    private final UserStorage userStorage;

    @Autowired
    public FilmService(@Qualifier("filmDbStorage") FilmStorage filmStorage,
                       LikeStorage likeStorage,
                       MpaStorage mpaStorage,
                       GenreStorage genreStorage,
                       @Qualifier("userDbStorage") UserStorage userStorage) {
        this.genreStorage = genreStorage;
        this.mpaStorage = mpaStorage;
        this.userStorage = userStorage;
        this.filmStorage = filmStorage;
        this.likeStorage = likeStorage;
    }

    static final LocalDate START_RELEASE_DATA = LocalDate.of(1895, 12, 28);

    @Override
    public void validate(Film data) {
        if (data.getReleaseDate().isBefore(START_RELEASE_DATA)) {
            throw new ValidationException("Film release data is invalid");
        }
    }

    @Override
    public Film create(Film data) {
        validate(data);
        Film newFilm = filmStorage.create(data);
        Mpa mpa = mpaStorage.getById(newFilm.getMpa().getId());
        genreStorage.addFilmGenres(newFilm.getId(), new ArrayList<>(data.getGenres()));
        Set<Genre> genres = new HashSet<>(genreStorage.getFilmGenres(newFilm.getId()));
        return newFilm.toBuilder()
                .genres(genres)
                .mpa(mpa)
                .build();
    }

    @Override
    public Film update(Film film) {
        Film filmCheck = checkStorageFilm(film.getId());
        validate(film);
        Film updatedFilm = filmStorage.update(film);
        Mpa mpa = mpaStorage.getById(updatedFilm.getMpa().getId());
        genreStorage.addFilmGenres(updatedFilm.getId(), new ArrayList<>(film.getGenres()));
        Set<Genre> genres = new HashSet<>(genreStorage.getFilmGenres(updatedFilm.getId()));
        return updatedFilm.toBuilder()
                .genres(genres)
                .mpa(mpa)
                .build();
    }

    @Override
    public List<Film> getAll() {
        return filmStorage.getAll();
    }

    @Override
    public Film getById(Integer id) {
        return filmStorage.getById(id);
    }

    public void deleteLike(Integer filmId, Integer userId) {
        Film film = checkStorageFilm(filmId);
        User user = checkStorageUser(userId);
        likeStorage.deleteLike(filmId,userId);
    }

    public void addLike(Integer filmId, Integer userId) {
        Film film = checkStorageFilm(filmId);
        User user = checkStorageUser(userId);
        likeStorage.addLike(filmId, userId);
    }

    public List<Film> getAllPopular(Integer count) {
        return likeStorage.getPopular(count);
    }

    private Film checkStorageFilm(Integer id) {
        Optional<Film> filmOptional = Optional.ofNullable(filmStorage.getById(id));
        return filmOptional.orElseThrow(() -> new DataNotFoundException("Не найден id"));
    }

    private User checkStorageUser(Integer id) {
        Optional<User> userOptional = Optional.ofNullable(userStorage.getById(id));
        return userOptional.orElseThrow(() -> new DataNotFoundException("Не найден id"));
    }
}
