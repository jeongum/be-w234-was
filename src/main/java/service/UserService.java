package service;

import model.User;
import repository.UserMemoryRepository;
import repository.UserRepository;

import java.security.InvalidParameterException;
import java.util.Map;
import java.util.Optional;

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
            throw new InvalidParameterException();
        }

        User user = userRepository.findById(body.get("userId")).orElseThrow(InvalidParameterException::new);

        return (user.getPassword().equals(body.get("password"))) ? true : false;
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
