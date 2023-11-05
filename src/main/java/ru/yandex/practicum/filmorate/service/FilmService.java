package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService extends AbstractService<Film> {

    private final FilmStorage filmStorage;

    private final UserStorage userStorage;

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
        return filmStorage.create(data);
    }

    @Override
    public Film update(Film data) {
        validate(data);
        return filmStorage.update(data);
    }

    @Override
    public List<Film> getAll() {
        return filmStorage.getAll();
    }


    @Override
    public Film getById(Long id) {
        return filmStorage.getById(id);
    }

    public void deleteLike(Long id, Long userId) {
        Film film = checkStorageFilm(id);
        User user = checkStorageUser(userId);
        filmStorage.deleteLike(film,user);
    }

    public Film addLike(Long id, Long userId) {
        Film film = checkStorageFilm(id);
        User user = checkStorageUser(userId);
        return filmStorage.addLike(film, user);
    }

    public List<Film> getAllPopular(Long count) {
        List<Film> storage = filmStorage.getPopular();
        return storage.stream()
                .sorted(Comparator.comparingInt(p0 -> -p0.getIds().size()))
                .limit(count)
                .collect(Collectors.toList());
    }

    private Film checkStorageFilm(Long  id) {
        Optional<Film> filmOptional = Optional.ofNullable(filmStorage.getById(id));
        return filmOptional.orElseThrow(() -> new DataNotFoundException("Не найден id"));
    }

    private User checkStorageUser(Long  id) {
        Optional<User> userOptional = Optional.ofNullable(userStorage.getById(id));
        return userOptional.orElseThrow(() -> new DataNotFoundException("Не найден id"));
    }
}
