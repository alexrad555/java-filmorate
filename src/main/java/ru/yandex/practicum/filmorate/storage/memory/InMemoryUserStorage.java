package ru.yandex.practicum.filmorate.storage.memory;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class InMemoryUserStorage extends InMemoryBaseStorage<User> implements UserStorage {

    @Override
    public List<User> getAllFriends(Long id) {
        if (!super.getStorage().containsKey(id)) {
            throw new DataNotFoundException("Не найден id");
        }
        Set<Long> ids = super.getStorage().get(id).getIds();
        List<User> users = new ArrayList<>();
        for (Long userId : ids) {
            if (super.getStorage().containsKey(userId)) {
                User user = super.getStorage().get(userId);
                users.add(user);
            }
        }
        return users;
    }

    @Override
    public List<User> getCommonFriends(Long id, Long otherId) {
        List<User> users = new ArrayList<>();
        if (!super.getStorage().containsKey(id)) {
            throw new DataNotFoundException("Не найден id");
        }
        Set<Long> ids = new HashSet<>(super.getStorage().get(id).getIds());
        if (!super.getStorage().containsKey(id)) {
            throw new DataNotFoundException("Не найден id");
        }
        Set<Long> idsOther = super.getStorage().get(otherId).getIds();
        ids.retainAll(idsOther);
        for (Long userId : ids) {
            users.add(super.getStorage().get(userId));
        }
        return users;
    }

    @Override
    public User addFriend(Long id, Long userId) {
        if (!super.getStorage().containsKey(id) || !super.getStorage().containsKey(userId)) {
            throw new DataNotFoundException("Не найден id");
        }
        User user = super.getStorage().get(id);
        user.getIds().add(userId);
        User userSecond = super.getStorage().get(userId);
        userSecond.getIds().add(id);
        return user;
    }

    @Override
    public void deleteFriend(Long id, Long userId) {
        if (!super.getStorage().containsKey(id)) {
            throw new DataNotFoundException("Не найден id");
        }
        User user = super.getStorage().get(id);
        user.getIds().remove(userId);
        if (!super.getStorage().containsKey(userId)) {
            throw new DataNotFoundException("Не найден id");
        }
        User userSecond = super.getStorage().get(userId);
        userSecond.getIds().remove(id);
    }
}
