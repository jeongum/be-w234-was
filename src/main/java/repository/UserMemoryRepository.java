package repository;

import model.User;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserMemoryRepository implements UserRepository {

    private static UserMemoryRepository instance = new UserMemoryRepository();

    private UserMemoryRepository() {
        users = new HashMap<>();
    }

    public static UserMemoryRepository getInstance() {
        return instance;
    }

    private Map<String, User> users;

    @Override
    public User save(User user) {
        if (users.containsKey(user.getUserId())) {
            throw new InvalidParameterException("이미 존재하는 회원입니다.");
        }
        users.put(user.getUserId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(String userId) {
        User user = users.get(userId);
        return Optional.ofNullable(user);
    }
}
