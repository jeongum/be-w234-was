package service;

import model.User;
import repository.UserMemoryRepository;
import repository.UserRepository;

import java.security.InvalidParameterException;
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
        if (!body.containsKey("userId")) throw new InvalidParameterException("userId가 없습니다.");
        if (!body.containsKey("password")) throw new InvalidParameterException("password가 없습니다.");
        if (!body.containsKey("name")) throw new InvalidParameterException("name가 없습니다.");
        if (!body.containsKey("email")) throw new InvalidParameterException("email가 없습니다.");

        User user = new User(body.get("userId"), body.get("password"), body.get("name"), body.get("email"));
        userRepository.save(user);

        return user;
    }
}
