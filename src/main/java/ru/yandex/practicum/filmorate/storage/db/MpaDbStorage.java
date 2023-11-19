package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MpaDbStorage implements MpaStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Mpa> getAll() {
        String slqQuery = "select * from mpa ORDER BY id";
        return jdbcTemplate.query(slqQuery, MpaDbStorage::createMpa);
    }

    @Override
    public Mpa getById(Integer id) {
        String slqQuery = "select * from mpa WHERE id = ?";
        List<Mpa> mpas = jdbcTemplate.query(slqQuery, MpaDbStorage::createMpa, id);
        if (mpas.size() != 1) {
            throw new DataNotFoundException("mpa with id not single");
        }
        return mpas.get(0);
    }

    static Mpa createMpa(ResultSet rs, int rowNum) throws SQLException {
        return Mpa.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .build();
    }
}
