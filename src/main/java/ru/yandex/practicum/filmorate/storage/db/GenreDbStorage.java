package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GenreDbStorage implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;
    @Override
    public List<Genre> getAll() {
        String slqQuery = "select * from genres ORDER BY id";
        return jdbcTemplate.query(slqQuery, GenreDbStorage::createGenre);
    }

    @Override
    public Genre getById(Integer id) {
        String slqQuery = "select * from genres where id = ?";
        List<Genre> genres = jdbcTemplate.query(slqQuery, GenreDbStorage::createGenre, id);
        if (genres.size() != 1) {
            throw new DataNotFoundException("genre with id not single");
        }
        return genres.get(0);
    }

    @Override
    public List<Genre> getFilmGenres(Integer filmId) {
        String slqQuery = "SELECT g.id, g.name " +
                "FROM film_genre fg " +
                "LEFT JOIN films f ON fg.film_id = f.id " +
                "RIGHT JOIN genres g ON fg.genre_id = g.id " +
                "WHERE f.id = ? " +
                "ORDER BY g.id";
        return jdbcTemplate.query(slqQuery, GenreDbStorage::createGenre, filmId);
    }

    @Override
    public void addFilmGenres(Integer filmId, List<Genre> genres) {
        deleteFilmGenres(filmId);
        String slqQuery = "INSERT INTO film_genre (film_id, genre_id) VALUES (?, ?)";
        for (Genre genre : genres) {
            getById(genre.getId());
            jdbcTemplate.update(slqQuery, filmId, genre.getId());
        }
    }

    @Override
    public void deleteFilmGenres(Integer filmId) {
        String slqQuery = "DELETE FROM film_genre WHERE film_id = ?";
        jdbcTemplate.update(slqQuery, filmId);
    }

    static Genre createGenre(ResultSet rs, int rowNum) throws SQLException {
        return Genre.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .build();
    }
}
