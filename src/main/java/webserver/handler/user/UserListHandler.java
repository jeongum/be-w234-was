package webserver.handler.user;

import constant.Path;
import lombok.extern.slf4j.Slf4j;
import service.UserService;
import webserver.handler.Handler;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatusCode;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class UserListHandler implements Handler {

    private UserService userService = UserService.getInstance();

    @Override
    public HttpResponse handle(HttpRequest request) {
        Map<String, String> header = new HashMap<>();
        if (isLogin(request.getCookie())){
            return new HttpResponse(HttpStatusCode.OK, header, "wjddma".getBytes());
        }
        header.put("location", "http://" + request.getHeader().get("Host") + Path.LOGIN);
        return new HttpResponse(HttpStatusCode.FOUND, header);
    }

    private boolean isLogin(Map<String, String> cookie) {
        String login = cookie.getOrDefault("logined", String.valueOf(false));
        return (login.equals("true")) ? true : false;
    }
}
