package repository;

import model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    public User save(User user);

    public Optional<User> findById(String userId);

    void deleteAll();

    int count();

    List<User> findAll();
}
