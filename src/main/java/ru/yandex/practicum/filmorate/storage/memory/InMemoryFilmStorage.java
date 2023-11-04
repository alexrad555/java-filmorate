package ru.yandex.practicum.filmorate.storage.memory;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.List;
import java.util.ArrayList;

@Component
public class InMemoryFilmStorage extends InMemoryBaseStorage<Film> implements FilmStorage {

    @Override
    public List<Film> getPopular() {
        return new ArrayList<>(super.getStorage().values());
    }

    @Override
    public Film addLike(Film film, User user) {
        film.getIds().add(user.getId());
        return film;
    }

    @Override
    public void deleteLike(Film film, User user) {
        film.getIds().remove(user.getId());
    }

}
