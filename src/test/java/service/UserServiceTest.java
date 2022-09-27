package service;

import exception.UserException;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.UserDBRepository;
import repository.UserMemoryRepository;
import repository.UserRepository;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserService userService = UserService.getInstance();
    private UserRepository userRepository = UserDBRepository.getInstance();

    @Test
    @DisplayName("맵 데이터로 유저를 생성한다.")
    void createUser() {
        // given
        Map<String, String> params = new HashMap<>();
        params.put("userId", "userId");
        params.put("password", "password");
        params.put("name", "name");
        params.put("email", "email");

        // when
        User user = userService.createUser(params);

        // then
        assertEquals(user.getName(), "name");
    }

    @Test
    @DisplayName("맵 데이터가 없을 시, InvalidParameterException을 리턴한다.")
    void createUserWithException() {
        // given
        Map<String, String> params = new HashMap<>();
        params.put("userId", "userId");
        params.put("password", "password");
        params.put("name", "name");

        // when then
        assertThrows(UserException.class, () ->{
            userService.createUser(params);
        });
    }

    @Test
    @DisplayName("맵 데이터로 로그인을 한다.")
    void login() {
        // given
        saveUser();
        Map<String, String> params = new HashMap<>();
        params.put("userId", "userId");
        params.put("password", "password");

        // when
        boolean login = userService.login(params);

        // then
        assertTrue(login);
    }

    @Test
    @DisplayName("올바르지 않은 비밀번호로 로그인에 실패한다.")
    void loginWithWrongPW() {
        // given
        saveUser();
        Map<String, String> params = new HashMap<>();
        params.put("userId", "userId");
        params.put("password", "wrongPW");

        // when
        boolean login = userService.login(params);

        // then
        assertFalse(login);
    }

    private void saveUser() {
        userRepository.save(new User("userId", "password", "name", "email"));
    }
}