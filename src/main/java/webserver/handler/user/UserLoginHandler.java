package webserver.handler.user;

import constant.Path;
import lombok.extern.slf4j.Slf4j;
import service.UserService;
import webserver.handler.Handler;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatusCode;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class UserLoginHandler implements Handler {

    private UserService userService = UserService.getInstance();

    @Override
    public HttpResponse handle(HttpRequest request) {
        Map<String, String> header = new HashMap<>();
        Map<String, String> cookies = new HashMap<>();

        boolean logined;
        try {
            logined = userService.login(request.getBody());
        } catch (InvalidParameterException e) {
            logined = false;
        }

        String location = (logined) ? Path.HOME : Path.LOGIN_FAILED;
        header.put("location", "http://" + request.getHeader().get("Host") + location);
        cookies.put("logined", String.valueOf(logined));
        return new HttpResponse(HttpStatusCode.FOUND, header, cookies);
    }
}
