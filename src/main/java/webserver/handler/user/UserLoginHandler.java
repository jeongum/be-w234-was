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

        if (userService.login(request.getBody())) {

            header.put("location", "http://" + request.getHeader().get("Host") + Path.HOME);
            cookies.put("logined", "true");
            return new HttpResponse(HttpStatusCode.FOUND, header, cookies);
        }

        header.put("location", "http://" + request.getHeader().get("Host") + Path.LOGIN_FAILED);
        cookies.put("logined", "false");
        return new HttpResponse(HttpStatusCode.FOUND, header, cookies);
    }
}
