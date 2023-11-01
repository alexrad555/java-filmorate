package ru.yandex.practicum.filmorate.storage.memory;

import lombok.Setter;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.exception.InternalServerErrorException;
import ru.yandex.practicum.filmorate.model.BaseUnit;
import ru.yandex.practicum.filmorate.storage.AbstractStorage;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class InMemoryBaseStorage<T extends BaseUnit> implements AbstractStorage<T> {

    private final Map<Long, T> storage = new HashMap<>();
    private long generatedId;

    @Override
    public T create(T data) {
        if (data.getId() != null) {
            throw new InternalServerErrorException("не должен приходить id");
        }
        data.setId(++generatedId);
        storage.put(data.getId(), data);
        return data;
    }

    @Override
    public T update(T data) {
        if (!storage.containsKey(data.getId())) {
            throw new DataNotFoundException("Не найден id");
        }
        storage.put(data.getId(), data);
        return data;
    }

    @Override
    public List<T> getAll() {
        return new ArrayList<>(storage.values());
    }

}
