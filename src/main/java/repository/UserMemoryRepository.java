package repository;

import exception.UserException;
import exception.UserExceptionMessage;
import model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class UserMemoryRepository implements UserRepository {

    private static UserMemoryRepository instance = new UserMemoryRepository();

    private UserMemoryRepository() {
        users = new ConcurrentHashMap<>();
    }

    public static UserMemoryRepository getInstance() {
        return instance;
    }

    private Map<String, User> users;

    @Override
    public User save(User user) {
        if (users.containsKey(user.getUserId())) {
            throw new UserException(UserExceptionMessage.DUPLICATE_USER);
        }
        users.put(user.getUserId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(String userId) {
        User user = users.get(userId);
        if (user == null) {
            throw new UserException(UserExceptionMessage.USER_NOT_FOUND);
        }
        return Optional.of(user);
    }

    @Override
    public void deleteAll() {
        users.clear();
    }

    @Override
    public int count() {
        return users.size();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }


}
