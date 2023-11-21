package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@JdbcTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserDbStorageTest {

    private final JdbcTemplate jdbcTemplate;
    private UserStorage userStorage;
    private FriendStorage friendStorage;
    private User user1;
    private User user2;
    private User user3;
    private User userTest;

    @BeforeEach
    void setUp() {
        userStorage = new UserDbStorage(jdbcTemplate);
        friendStorage = new FriendDbStorage(jdbcTemplate);
        user1 = User.builder()
                .id(1)
                .email("user1@user.com")
                .login("Man1")
                .name("User1")
                .birthday(LocalDate.of(2000, 8, 20))
                .build();
        user2 = User.builder()
                .id(2)
                .email("user2@user.com")
                .login("Man2")
                .name("User2")
                .birthday(LocalDate.of(1990, 8, 20))
                .build();
        userTest = User.builder()
                .id(3)
                .name("User4")
                .email("user4@user.com")
                .login("Man4")
                .birthday(LocalDate.of(2001, 8, 20))
                .build();
        userStorage.create(user1);
        userStorage.create(user2);
    }



    @Test
    public void userCreateTest() {
        assertThat(userStorage.create(userTest))
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(userStorage.getById(3));
    }

    @Test
    void testUpdate() {
        userStorage.create(userTest);
        userTest.toBuilder()
                .name("Update name")
                .build();
        assertThat(userStorage.update(userTest))
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(userStorage.getById(3));
    }

    @Test
    void testFindAll() {
        assertThat(userStorage.getAll())
                .isNotNull();
    }

    @Test
    void testFindById() {
        assertThat(userStorage.getById(user2.getId()))
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(user2);
    }
}
