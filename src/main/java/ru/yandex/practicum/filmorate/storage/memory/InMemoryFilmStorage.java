package ru.yandex.practicum.filmorate.storage.memory;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
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
    public Film addLike(Long id, Long userId) {
        if (!super.getStorage().containsKey(id) || !super.getStorage().containsKey(userId)) {
            throw new DataNotFoundException("Не найден id");
        }
        Film film = super.getStorage().get(id);
        film.getIds().add(userId);
        return film;
    }

    @Override
    public void deleteLike(Long id, Long userId) {
        if (!super.getStorage().containsKey(id) || !super.getStorage().containsKey(userId)) {
            throw new DataNotFoundException("Не найден id");
        }
        Film film = super.getStorage().get(id);
        film.getIds().remove(userId);
    }

}
