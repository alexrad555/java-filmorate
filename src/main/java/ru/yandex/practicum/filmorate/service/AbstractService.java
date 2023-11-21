package ru.yandex.practicum.filmorate.service;

import java.util.List;

public abstract class AbstractService<T> {

    public abstract T create(T data);

    public abstract T update(T data);

    public abstract List<T> getAll();

    public abstract void validate(T data);

    public abstract T getById(Integer id);
}
