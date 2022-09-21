package webserver.handler.user;

import constant.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.UserMemoryRepository;
import repository.UserRepository;
import service.UserService;
import util.HttpRequestParser;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatusCode;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class UserCreateHandlerTest {

    private UserCreateHandler userCreateHandler = new UserCreateHandler();
    private UserRepository userRepository = UserMemoryRepository.getInstance();

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Request를 받아 회원가입에 성공하고 응답을 리턴한다.")
    void handle() throws IOException {
        // given
        byte[] requestStr = ("POST /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 59\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" + "\n" +
                "userId=javajigi&password=password&name=name&email=javajigi@slipp.net").getBytes();
        HttpRequest request = HttpRequestParser.parseHttpRequest(new BufferedReader(new InputStreamReader(new ByteArrayInputStream(requestStr), "UTF-8")));

        // when
        HttpResponse response = userCreateHandler.handle(request);

        // then
        assertEquals(HttpStatusCode.FOUND, response.getStatus());
        assertEquals("http://" + request.getHeader().get("Host") + Path.HOME, response.getHeader().get("location"));
    }

    @Test
    @DisplayName("Request를 받아 회원가입에 실패하고 응답을 리턴한다.")
    void handleWithException() throws IOException {
        // given
        byte[] errorRequestStr = ("POST /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 59\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" + "\n" +
                "userId=javajigi&password=password").getBytes();
        HttpRequest request = HttpRequestParser.parseHttpRequest(new BufferedReader(new InputStreamReader(new ByteArrayInputStream(errorRequestStr), "UTF-8")));

        // when
        HttpResponse response = userCreateHandler.handle(request);

        // then
        assertEquals(HttpStatusCode.FOUND, response.getStatus());
        assertEquals("http://" + request.getHeader().get("Host") + Path.JOIN, response.getHeader().get("location"));
    }
}