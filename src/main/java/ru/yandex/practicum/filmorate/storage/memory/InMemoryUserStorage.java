package ru.yandex.practicum.filmorate.storage.memory;

import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.exception.InternalServerErrorException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;


public class InMemoryUserStorage implements UserStorage {

    private final Map<Integer, User> storage = new HashMap<>();
    private Integer generatedId;

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
    public User getById(int id) {
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

    public List<User> getAllFriends(User user) {
        Set<Integer> ids = user.getIds();
        List<User> users = new ArrayList<>();
        for (Integer userId : ids) {
            if (storage.containsKey(userId)) {
                User userAdd = storage.get(userId);
                users.add(userAdd);
            }
        }
        return users;
    }

    public List<User> getCommonFriends(User user, User otherUser) {
        List<User> users = new ArrayList<>();
        Set<Integer> ids = new HashSet<>(user.getIds());
        Set<Integer> idsOther = otherUser.getIds();
        ids.retainAll(idsOther);
        for (Integer userId : ids) {
            users.add(storage.get(userId));
        }
        return users;
    }

    public User addFriend(User user, User friendUser) {
        user.getIds().add(friendUser.getId());
        friendUser.getIds().add(user.getId());
        return user;
    }

    public void deleteFriend(User user, User friendUser) {
        user.getIds().remove(friendUser.getId());
        friendUser.getIds().remove(user.getId());
    }
}
