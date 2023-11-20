package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.MpaStorage;
import ru.yandex.practicum.filmorate.storage.db.mapper.FilmExtractor;

import java.time.LocalDate;
import java.util.HashSet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FilmDbStorageTest {

    private final JdbcTemplate jdbcTemplate;
    private FilmStorage filmStorage;
    private MpaStorage mpaDbStorage;
    private Film film1;
    private Film film2;
    private Film testFilm;

    @BeforeEach
    void setUp() {
        filmStorage = new FilmDbStorage(jdbcTemplate, new FilmExtractor());
        mpaDbStorage = new MpaDbStorage(jdbcTemplate);

        film1 = Film.builder()
                .id(1)
                .name("film1")
                .description("description1")
                .releaseDate(LocalDate.parse("1983-01-15"))
                .duration(100)
                .mpa(mpaDbStorage.getById(1).toBuilder().name(null).build())
                .build();
        film2 = Film.builder()
                .id(2)
                .name("film2")
                .description("description2")
                .releaseDate(LocalDate.parse("1991-08-27"))
                .duration(100)
                .mpa(mpaDbStorage.getById(3).toBuilder().name("PG-13").build())
                .build();
        filmStorage.create(film1);
        filmStorage.create(film2);
        testFilm = Film.builder()
                .id(3)
                .name("film3")
                .description("description3")
                .releaseDate(LocalDate.parse("1985-05-07"))
                .duration(100)
                .mpa(mpaDbStorage.getById(5).toBuilder().name(null).build())
                .build();
    }

    @Test
    void testCreate() {
        assertThat(filmStorage.create(testFilm))
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(filmStorage.getById(3));
    }

    @Test
    void testUpdate() {
        filmStorage.create(testFilm);
        testFilm.toBuilder()
                .name("Update name")
                .build();
        assertThat(filmStorage.update(testFilm))
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(filmStorage.getById(3));
    }

    @Test
    void testFindAll() {
        assertThat(filmStorage.getAll())
                .isNotNull();
    }

    @Test
    void testFindById() {
        assertThat(filmStorage.getById(film2.getId()))
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(film2.toBuilder().genres(new HashSet<>()).build());
    }
}
