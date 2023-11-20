package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.LikeStorage;
import ru.yandex.practicum.filmorate.storage.db.mapper.FilmExtractor;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LikeDbStorage implements LikeStorage {

    private final JdbcTemplate jdbcTemplate;
    private final FilmExtractor filmExtractor;

    @Override
    public List<Film> getPopular(Integer count) {
        String sqlQuery = "select f.*, g.id as genre_id, g.name as genre_name, m.id as mpa_id, m.name as mpa_name" +
                "  from films f " +
                "  left join film_genre fg on fg.film_id = f.id " +
                "  left join genres g on g.id = fg.genre_id " +
                "  left join mpa m on m.id = f.mpa_id" +
                "  LEFT JOIN likes l ON f.id = l.film_id " +
                "  GROUP BY f.id " +
                "  ORDER BY COUNT(l.user_id) DESC " +
                "  LIMIT ?";
        return jdbcTemplate.query(sqlQuery, filmExtractor, count);
    }

    @Override
    public void addLike(Integer filmId, Integer userId) {
        String sqlQuery = "INSERT INTO likes (film_id, user_id) VALUES (?, ?)";
        jdbcTemplate.update(sqlQuery, filmId, userId);
    }

    @Override
    public void deleteLike(Integer filmId, Integer userId) {
        String sqlQuery = "DELETE FROM likes WHERE film_id = ? AND user_id = ?";
        jdbcTemplate.update(sqlQuery, filmId, userId);
    }

}
