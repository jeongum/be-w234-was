package repository;

import model.User;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

public class UserMemoryRepository implements UserRepository{

    Map<String, User> users = new HashMap<>();

    @Override
    public User save(User user) {
        if(users.containsKey(user.getUserId())){
            throw new InvalidParameterException("이미 존재하는 회원입니다.");
        }
        users.put(user.getUserId(), user);
        return user;
    }
}
