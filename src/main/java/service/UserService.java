package service;

import constant.Path;
import model.User;
import repository.UserMemoryRepository;
import repository.UserRepository;
import util.ByteArrayUtils;
import util.FileUtils;

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

        return (user.getPassword().equals(body.get("password"))) ? true : false;
    }

    public byte[] list() {
        byte[] header = FileUtils.getContents(Path.USER_LIST_HEADER);
        byte[] footer = FileUtils.getContents(Path.USER_LIST_FOOTER);
        byte[] content = generateUserList();

        return ByteArrayUtils.concat(header, content, footer);
    }

    public byte[] generateUserList() {
        List<User> users = userRepository.findAll();
        int idx = 1;
        StringBuilder sb = new StringBuilder();
        for (User u : users) {
            sb.append(generateUserListRow(u, idx++));
        }
        return sb.toString().getBytes();
    }

    private StringBuilder generateUserListRow(User u, int idx) {
        StringBuilder sb = new StringBuilder();
        sb.append("<tr>");
        sb.append("<th scope=\"row\">" + idx + "</th>");
        sb.append("<td>" + u.getUserId() + "</td>");
        sb.append("<td>" + u.getName() + "</td>");
        sb.append("<td>" + u.getEmail() + "</td>");
        sb.append("</tr>");
        return sb;
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
