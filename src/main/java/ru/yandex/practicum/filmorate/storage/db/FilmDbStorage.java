package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.db.mapper.FilmExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    private final FilmExtractor filmExtractor;

    static final String sqlQueryH2 = "select f.*, g.id as genre_id, g.name as genre_name, m.id as mpa_id, m.name as mpa_name" +
            "  from films f " +
            "  left join film_genre fg on fg.film_id = f.id " +
            "  left join genres g on g.id = fg.genre_id " +
            "  left join mpa m on m.id = f.mpa_id";

    @Override
    public Film create(Film film) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("films")
                .usingGeneratedKeyColumns("id");
        int filmId = simpleJdbcInsert.executeAndReturnKey(filmMap(film)).intValue();
        return getById(filmId);
    }

    @Override
    public Film update(Film film) {
        String sqlQuery = "UPDATE films SET name = ?, description = ?, release_date = ?, duration = ?, mpa_id = ? WHERE id = ?";
        jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId());
        return getById(film.getId());
    }

    @Override
    public List<Film> getAll() {
        return jdbcTemplate.query(sqlQueryH2, filmExtractor);
    }


    @Override
    public Film getById(Integer id) {
        String sqlQuery = sqlQueryH2 + "  where f.id = ?";
        List<Film> films = jdbcTemplate.query(sqlQuery, filmExtractor, id);
        if (films.size() != 1) {
            throw new DataNotFoundException(String.format("films with id %s not single", id));
        }
        return films.get(0);
    }


    private Map<String, String> filmMap(Film film) {
        return Map.of("name", film.getName(),
                "description", film.getDescription(),
                "release_date", String.valueOf(film.getReleaseDate()),
                "duration", String.valueOf(film.getDuration()),
                "mpa_id", String.valueOf(film.getMpa().getId()));
    }
}
