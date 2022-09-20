package repository;

import model.User;

import java.util.HashMap;
import java.util.Map;

public class UserMemoryRepository implements UserRepository{

    Map<Long, User> users = new HashMap<>();
    Long seq = (long)0;

    @Override
    public User save(User user) {
        users.put(seq++, user);
        return user;
    }
}
