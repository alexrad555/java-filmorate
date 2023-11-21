package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FriendDbStorage implements FriendStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<User> getAllFriends(Integer userId) {
        String sqlQuery = "SELECT u.* FROM users u RIGHT JOIN friends f ON u.id = f.friend_id " +
                "WHERE user_id = ? ORDER BY u.id";
        List<User> users = jdbcTemplate.query(sqlQuery, FriendDbStorage::createUser, userId);
        return users;
    }

    @Override
    public void addFriend(Integer userId, Integer friendId) {
        String sqlQuery = "INSERT INTO friends (user_id, friend_id) VALUES ("
                + userId + ", " + friendId + ")";
        jdbcTemplate.update(sqlQuery);
    }

    @Override
    public void deleteFriend(Integer userId, Integer friendId) {
        String sqlQuery = "DELETE FROM friends WHERE user_id = ? AND friend_id = ?";
        jdbcTemplate.update(sqlQuery, userId, friendId);
    }

    @Override
    public List<User> getCommonFriends(Integer userId, Integer otherId) {
        String sqlQuery = "SELECT u.* " +
                "FROM friends AS f1 " +
                "JOIN friends AS f2 ON f1.friend_id = f2.friend_id" +
                " JOIN users AS u ON f1.friend_id = u.id " +
                "WHERE f1.user_id = ? AND f2.user_id = ? AND f1.friend_id != ?";
        return jdbcTemplate.query(sqlQuery, FriendDbStorage::createUser, userId, otherId, userId);
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
}
