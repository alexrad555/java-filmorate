package ru.yandex.practicum.filmorate.storage.memory;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.exception.InternalServerErrorException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;


@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Integer, Film> storage = new HashMap<>();
    private Integer generatedId;

    @Override
    public Film create(Film data) {
        if (data.getId() != null) {
            throw new InternalServerErrorException("не должен приходить id");
        }
        data.setId(++generatedId);
        storage.put(data.getId(), data);
        return data;
    }

    @Override
    public Film getById(Integer id) {
        if (!storage.containsKey(id)) {
            throw new DataNotFoundException("Не найден id");
        }
        return storage.get(id);
    }

    @Override
    public Film update(Film data) {
        if (!storage.containsKey(data.getId())) {
            throw new DataNotFoundException("Не найден id");
        }
        storage.put(data.getId(), data);
        return data;
    }

    @Override
    public List<Film> getAll() {
        return new ArrayList<>(storage.values());
    }

    public List<Film> getPopular() {
        return new ArrayList<>(storage.values());
    }

    public Film addLike(Film film, User user) {
        film.getLikes().add(user.getId());
        return film;
    }

    public void deleteLike(Film film, User user) {
        film.getLikes().remove(user.getId());
    }

}
