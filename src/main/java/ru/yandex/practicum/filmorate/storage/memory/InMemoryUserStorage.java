package ru.yandex.practicum.filmorate.storage.memory;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class InMemoryUserStorage extends InMemoryBaseStorage<User> implements UserStorage {

    @Override
    public List<User> getAllFriends(User user) {
        Set<Long> ids = user.getIds();
        List<User> users = new ArrayList<>();
        for (Long userId : ids) {
            if (super.getStorage().containsKey(userId)) {
                User userAdd = super.getStorage().get(userId);
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
            users.add(super.getStorage().get(userId));
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
