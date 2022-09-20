package service.user;

import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.UserService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserServiceTest {

    private UserService userService = UserService.getInstance();

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
}