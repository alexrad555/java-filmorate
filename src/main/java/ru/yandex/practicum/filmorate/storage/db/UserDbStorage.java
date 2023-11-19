package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;
    @Override
    public User create(User data) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        int UserId = simpleJdbcInsert.executeAndReturnKey(userMap(data)).intValue();
        return getById(UserId);
    }

    @Override
    public User update(User data) {
        String sqlQuery = "UPDATE users SET email = ?, login = ?, name = ?, birthday = ? WHERE id = ?";
        jdbcTemplate.update(sqlQuery, data.getEmail(), data.getLogin(), data.getName(), data.getBirthday(), data.getId());
        return getById(data.getId());
    }

    @Override
    public List<User> getAll() {
        String slqQuery = "select * from users";
        return jdbcTemplate.query(slqQuery, UserDbStorage::createUser);
    }


    @Override
    public User getById(int id) {
        String slqQuery = "select * from users where id = ?";
        List<User> users = jdbcTemplate.query(slqQuery, UserDbStorage::createUser, id);
        if (users.size() != 1) {
            throw new DataNotFoundException(String.format("users with id %s not single", id));
        }
        return users.get(0);
    }

    private static User createUser(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .email(rs.getString("email"))
                .login(rs.getString("login"))
                .birthday(rs.getDate("birthday").toLocalDate())
                .build();
    }

    private Map<String, String> userMap(User user) {
        return Map.of("email", user.getEmail(),
                "login", user.getLogin(),
                "name", user.getName(),
                "birthday", String.valueOf(user.getBirthday()));
    }
}
