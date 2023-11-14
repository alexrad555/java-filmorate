package ru.yandex.practicum.filmorate.storage.memory;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.exception.InternalServerErrorException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> storage = new HashMap<>();
    private long generatedId;

    @Override
    public User create(User data) {
        if (data.getId() != null) {
            throw new InternalServerErrorException("не должен приходить id");
        }
        data.setId(++generatedId);
        storage.put(data.getId(), data);
        return data;
    }

    @Override
    public User getById(Long id) {
        if (!storage.containsKey(id)) {
            throw new DataNotFoundException("Не найден id");
        }
        return storage.get(id);
    }

    @Override
    public User update(User data) {
        if (!storage.containsKey(data.getId())) {
            throw new DataNotFoundException("Не найден id");
        }
        storage.put(data.getId(), data);
        return data;
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public List<User> getAllFriends(User user) {
        Set<Long> ids = user.getIds();
        List<User> users = new ArrayList<>();
        for (Long userId : ids) {
            if (storage.containsKey(userId)) {
                User userAdd = storage.get(userId);
                users.add(userAdd);
            }
        }
        return users;
    }

    @Override
    public List<User> getCommonFriends(User user, User otherUser) {
        List<User> users = new ArrayList<>();
        Set<Long> ids = new HashSet<>(user.getIds());
        Set<Long> idsOther = otherUser.getIds();
        ids.retainAll(idsOther);
        for (Long userId : ids) {
            users.add(storage.get(userId));
        }
        return users;
    }

    @Override
    public User addFriend(User user, User friendUser) {
        user.getIds().add(friendUser.getId());
        friendUser.getIds().add(user.getId());
        return user;
    }

    @Override
    public void deleteFriend(User user, User friendUser) {
        user.getIds().remove(friendUser.getId());
        friendUser.getIds().remove(user.getId());
    }
}
