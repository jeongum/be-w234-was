package service;

import model.User;
import repository.UserMemoryRepository;
import repository.UserRepository;

import java.security.InvalidParameterException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class UserService {

    private UserRepository userRepository;
    private static UserService instance = new UserService();

    private UserService() {
        userRepository = UserMemoryRepository.getInstance();
    }

    public static UserService getInstance() {
        return instance;
    }

    public User createUser(Map<String, String> body) {
        if (!validCreateData(body)) {
            throw new InvalidParameterException();
        }

        User user = new User(body.get("userId"), body.get("password"), body.get("name"), body.get("email"));
        userRepository.save(user);

        return user;
    }

    public boolean login(Map<String, String> body) {
        if (!validLoginData(body)) {
            return false;
        }

        AtomicBoolean login = new AtomicBoolean(false);
        userRepository.findById(body.get("userId")).ifPresent(user -> {
            if (user.getPassword().equals(body.get("password"))) login.set(true);
        });

        return login.get();
    }

    private boolean validLoginData(Map<String, String> body) {
        if (!body.containsKey("userId") || !body.containsKey("password")) {
            return false;
        }
        return true;
    }

    private boolean validCreateData(Map<String, String> body) {
        if (!body.containsKey("userId") || !body.containsKey("password") || !body.containsKey("name") || !body.containsKey("email")) {
            return false;
        }
        return true;
    }
}
