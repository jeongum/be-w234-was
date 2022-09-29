package service;

import exception.UserException;
import exception.UserExceptionMessage;
import model.User;
import repository.UserDBRepository;
import repository.UserRepository;
import webserver.handler.dto.UserCreateDto;
import webserver.handler.dto.UserLoginDto;

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

    public User createUser(UserCreateDto dto) {
        User user = new User(dto.getUserId(), dto.getPassword(), dto.getName(), dto.getEmail());
        userRepository.save(user);

        return user;
    }

    public String login(UserLoginDto dto) {
        if (userRepository.findById(dto.getUserId()).isEmpty()) return null;

        User user = userRepository.findById(dto.getUserId()).get();

        return (user.getPassword().equals(dto.getPassword())) ? user.getUserId() : null;
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
