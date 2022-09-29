package service;

import exception.UserException;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.UserDBRepository;
import repository.UserMemoryRepository;
import repository.UserRepository;
import webserver.handler.dto.UserCreateDto;
import webserver.handler.dto.UserLoginDto;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserService userService = UserService.getInstance();
    private UserRepository userRepository = UserDBRepository.getInstance();

    private User user;

    @Test
    @DisplayName("맵 데이터로 유저를 생성한다.")
    void createUser() {
        // given
        UserCreateDto dto = UserCreateDto.builder()
                .userId(UUID.randomUUID().toString())
                .password("password")
                .name("name")
                .email("email")
                .build();

        // when
        User user = userService.createUser(dto);

        // then
        assertEquals(user.getName(), "name");
    }

    @Test
    @DisplayName("맵 데이터로 로그인을 한다.")
    void login() {
        // given
        saveUser();
        UserLoginDto dto = UserLoginDto.builder()
                .userId(user.getUserId())
                .password("password")
                .build();

        // when
        String login = userService.login(dto);

        // then
        assertEquals(user.getUserId(), login);
    }

    private void saveUser() {
        user = userRepository.save(new User(UUID.randomUUID().toString(), "password", "name", "email"));
    }
}