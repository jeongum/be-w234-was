package service;

import model.User;
import repository.UserMemoryRepository;
import repository.UserRepository;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;

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

        return user.getPassword().equals(body.get("password"));
    }

    public List<User> list(){
        return userRepository.findAll();
    }

    private boolean validLoginData(Map<String, String> body) {
        return body.containsKey("userId") && body.containsKey("password");
    }

    private boolean validCreateData(Map<String, String> body) {
        return body.containsKey("userId") && body.containsKey("password") && body.containsKey("name") && body.containsKey("email");
    }
}
