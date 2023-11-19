package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.util.List;

@Service
public class MpaService extends AbstractService<Mpa>{

    public MpaStorage mpaStorage;

    @Autowired
    public MpaService(MpaStorage mpaStorage) {
        this.mpaStorage = mpaStorage;
    }

    @Override
    public Mpa getById(Integer id) {
        return mpaStorage.getById(id);
    }

    @Override
    public List<Mpa> getAll() {
        return mpaStorage.getAll();
    }
    @Override
    public Mpa create(Mpa data) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Mpa update(Mpa data) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void validate(Mpa data) {
        throw new UnsupportedOperationException();
    }

}
