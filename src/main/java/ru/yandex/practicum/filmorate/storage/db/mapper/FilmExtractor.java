package ru.yandex.practicum.filmorate.storage.db.mapper;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class FilmExtractor implements ResultSetExtractor<List<Film>> {

    @Override
    public List<Film> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Film currentFilm = Film.builder().build();
        List<Film> filmList = new ArrayList<>();
        Set<Genre> genreSet = new LinkedHashSet<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            if (currentFilm.getId() == null || currentFilm.getId() != id) {

                currentFilm.setGenres(genreSet);


                Mpa mpa = Mpa.builder()
                        .id(rs.getInt("mpa_id"))
                        .name(rs.getString("mpa_name"))
                        .build();

                currentFilm = Film.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .description(rs.getString("description"))
                        .releaseDate(rs.getDate("release_date").toLocalDate())
                        .duration(rs.getInt("duration"))
                        .mpa(mpa)
                        .build();
                filmList.add(currentFilm);
                genreSet = new HashSet<>();
            }
            int genreId = rs.getInt("genre_id");
            if (genreId == 0) {
                continue;
            }
            Genre genre = Genre.builder()
                    .id(rs.getInt("genre_id"))
                    .name(rs.getString("genre_name"))
                    .build();
            genreSet.add(genre);
        }

        currentFilm.setGenres(genreSet);

        return filmList;
    }
}
