package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class GenreDbStorageTest {
    private final JdbcTemplate jdbcTemplate;
    private GenreStorage genreStorage;
    private Genre genre;

    @BeforeEach
    void setUp() {
        genreStorage = new GenreDbStorage(jdbcTemplate);
        genre = Genre.builder()
                .id(1)
                .name("Комедия")
                .build();
    }

    @Test
    void testFindAll() {
        assertThat(genreStorage.getAll())
                .isNotNull();
    }

    @Test
    void testFindById() {
        assertThat(genreStorage.getById(genre.getId()))
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(genre);
    }
}
