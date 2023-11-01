package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmorateValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService extends AbstractService<Film> {

    private final FilmStorage filmStorage;

    static final LocalDate START_RELEASE_DATA = LocalDate.of(1895, 12, 28);

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
    public void validate(Film data) {
        if (data.getReleaseDate().isBefore(START_RELEASE_DATA)) {
            throw new FilmorateValidationException("Film release data is invalid");
        }
    }

    @Override
    public Film getById(Long id) {
        return filmStorage.getById(id);
    }

    public void deleteLike(Long id, Long userId) {
        filmStorage.deleteLike(id,userId);
    }

    public Film addLike(Long id, Long userId) {
        return filmStorage.addLike(id, userId);
    }

    public List<Film> getAllPopular(Long count) {
        Long limit = count != null ? count : 10;
        List<Film> storage = filmStorage.getPopular();
        return storage.stream()
                .sorted(Comparator.comparingInt(p0 -> p0.getIds().size()))
                .limit(limit)
                .collect(Collectors.toList());
    }
}
