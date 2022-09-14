package controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpMethod;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    private UserController userController = new UserController();

    @Test
    @DisplayName("request를 받아 유저객체를 만든 후, 이름을 리턴한다.")
    void create() {
        // given
        HttpRequest request = new HttpRequest(HttpMethod.GET, "/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");

        // when
        HttpResponse response= userController.create(request);

        // then
        String name = new String(response.getBody());
        assertEquals(name, "javajigi");
    }
}