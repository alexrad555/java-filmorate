package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MpaDbStorageTest {

    private final JdbcTemplate jdbcTemplate;
    private MpaStorage mpaStorage;
    private Mpa mpa;

    @BeforeEach
    void setUp() {
        mpaStorage = new MpaDbStorage(jdbcTemplate);
        mpa = Mpa.builder()
                .id(1)
                .name("G")
                .build();
    }

    @Test
    void testFindAll() {
        assertThat(mpaStorage.getAll())
                .isNotNull();
    }

    @Test
    void testFindById() {
        assertThat(mpaStorage.getById(mpa.getId()))
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(mpa);
    }
}
