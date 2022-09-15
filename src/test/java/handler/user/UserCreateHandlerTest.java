package handler.user;

import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.request.HttpRequest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UserCreateHandlerTest {

    private UserCreateHandler userCreateHandler = new UserCreateHandler();

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
        User user = userCreateHandler.createUser(params);

        // then
        assertEquals(user.getName(), "name");
    }
}