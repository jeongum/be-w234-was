package service;

import exception.UserException;
import exception.UserExceptionMessage;
import model.User;
import repository.UserDBRepository;
import repository.UserRepository;

import java.util.List;
import java.util.Map;

public class UserService {

    private UserRepository userRepository;
    private static UserService instance = new UserService();

    private UserService() {
        userRepository = UserDBRepository.getInstance();
    }

    public static UserService getInstance() {
        return instance;
    }

    public User createUser(Map<String, String> body) {
        validCreateData(body);

        User user = new User(body.get("userId"), body.get("password"), body.get("name"), body.get("email"));
        userRepository.save(user);

        return user;
    }

    public boolean login(Map<String, String> body) {
        validLoginData(body);

        User user = userRepository.findById(body.get("userId")).get();

        return user.getPassword().equals(body.get("password"));
    }

    public List<User> list() {
        return userRepository.findAll();
    }

    private void validLoginData(Map<String, String> body) {
        if (!(body.containsKey("userId") && body.containsKey("password"))) {
            throw new UserException(UserExceptionMessage.INVALID_USER_PARAMETER);
        }
    }

    private void validCreateData(Map<String, String> body) {
        if (!(body.containsKey("userId") && body.containsKey("password") && body.containsKey("name") && body.containsKey("email"))) {
            throw new UserException(UserExceptionMessage.INVALID_USER_PARAMETER);
        }
    }
}
