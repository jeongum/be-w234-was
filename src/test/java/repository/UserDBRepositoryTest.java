package repository;

import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserDBRepositoryTest {

    UserRepository userRepository = UserDBRepository.getInstance();
    User user;

    @BeforeEach
    void setUp() {
        user = User.builder().userId(UUID.randomUUID().toString()).name("name").password("password").build();
    }

    @Test
    void save() {
        // given
        // when
        User savedUser = userRepository.save(user);

        // then
        assertEquals(user.getUserId(), savedUser.getUserId());
        assertEquals("name", savedUser.getName());
        assertEquals("password", savedUser.getPassword());
    }

    @Test
    void findById() {
        // given
        userRepository.save(user);

        // when
        Optional<User> OptionalFindUser = userRepository.findById(user.getUserId());

        // then
        User findUser = OptionalFindUser.get();
        assertEquals("name", findUser.getName());
        assertEquals("password", findUser.getPassword());
    }

    @Test
    void findByIdWithNull() {
        // given
        userRepository.save(user);

        // when
        Optional<User> OptionalFindUser = userRepository.findById(UUID.randomUUID().toString());

        // then
        assertThrows(NoSuchElementException.class, () -> OptionalFindUser.get());
    }

    @Test
    void deleteAll() {
        // given
        userRepository.save(user);

        // when
        userRepository.deleteAll();

        // then
        assertEquals(0, userRepository.findAll().size());
    }

    @Test
    void count() {
        // given
        int originSize = userRepository.findAll().size();
        userRepository.save(user);

        // when
        int count = userRepository.count();

        // then
        assertEquals(originSize + 1, count);
    }

    @Test
    void findAll() {
        // given
        int originSize = userRepository.findAll().size();
        userRepository.save(user);

        // when
        List<User> users = userRepository.findAll();

        // then
        assertEquals(originSize + 1, users.size());
        assertEquals(user.getUserId(), users.get(users.size() - 1).getUserId());
    }
}