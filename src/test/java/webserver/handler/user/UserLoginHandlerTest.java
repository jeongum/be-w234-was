package webserver.handler.user;

import constant.Path;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.UserDBRepository;
import repository.UserMemoryRepository;
import repository.UserRepository;
import util.HttpRequestParser;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatusCode;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.*;

class UserLoginHandlerTest {

    private UserLoginHandler userLoginHandler = new UserLoginHandler();
    private UserRepository userRepository = UserDBRepository.getInstance();

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        userRepository.save(new User("userId", "password", "name", "email"));
    }

    @Test
    @DisplayName("요청을 받아 로그인에 성공하고 응답을 리턴한다.")
    void handle() throws Exception{
        // given
        byte[] requestStr = ("POST /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 59\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" + "\n" +
                "userId=userId&password=password").getBytes();
        HttpRequest request = HttpRequestParser.parse(new BufferedReader(new InputStreamReader(new ByteArrayInputStream(requestStr), "UTF-8")));

        // when
        HttpResponse response = userLoginHandler.handle(request);

        // then
        assertEquals(HttpStatusCode.FOUND, response.getStatus());
        assertEquals("http://" + request.getHeader().get("Host") + Path.HOME, response.getHeader().get("location"));
        assertEquals("true", response.getCookies().get("logined"));
    }

    @Test
    @DisplayName("요청을 받아 로그인에 실패하고 응답을 리턴한다.")
    void handleWithException() throws Exception{
        // given
        byte[] requestStr = ("POST /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 59\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" + "\n" +
                "userId=wrongId&password=password").getBytes();
        HttpRequest request = HttpRequestParser.parse(new BufferedReader(new InputStreamReader(new ByteArrayInputStream(requestStr), "UTF-8")));

        // when
        HttpResponse response = userLoginHandler.handle(request);

        // then
        assertEquals(HttpStatusCode.FOUND, response.getStatus());
        assertEquals("http://" + request.getHeader().get("Host") + Path.LOGIN_FAILED, response.getHeader().get("location"));
        assertEquals("false", response.getCookies().get("logined"));
    }
}