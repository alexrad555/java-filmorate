package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;
    @Override
    public Film create(Film film) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("films")
                .usingGeneratedKeyColumns("id");
        int FilmId = simpleJdbcInsert.executeAndReturnKey(filmMap(film)).intValue();
        return getById(FilmId);
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
        String slqQuery = "select * from films";
        return jdbcTemplate.query(slqQuery, FilmDbStorage::createFilm);
    }

    @Override
    public Film getById(Integer id) {
        String slqQuery = "select * from films where id = ?";
        List<Film> films = jdbcTemplate.query(slqQuery, FilmDbStorage::createFilm, id);
        if (films.size() != 1) {
            throw new DataNotFoundException(String.format("films with id %s not single", id));
        }
        return films.get(0);
    }

    private static Film createFilm(ResultSet rs, int rowNum) throws SQLException {
        return Film.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .releaseDate(rs.getDate("release_date").toLocalDate())
                .duration(rs.getInt("duration"))
                .mpa(Mpa.builder().id(rs.getInt("mpa_id")).build())
                .build();
    }

    private Map<String, String> filmMap(Film film) {
        return Map.of("name", film.getName(),
                "description", film.getDescription(),
                "release_date", String.valueOf(film.getReleaseDate()),
                "duration", String.valueOf(film.getDuration()),
                "mpa_id", String.valueOf(film.getMpa().getId()));
    }
}
