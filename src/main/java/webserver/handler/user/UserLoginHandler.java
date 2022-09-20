package webserver.handler.user;

import service.UserService;
import webserver.handler.Handler;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatusCode;

import java.util.HashMap;
import java.util.Map;

public class UserLoginHandler implements Handler {

    private UserService userService = UserService.getInstance();

    @Override
    public HttpResponse handle(HttpRequest request) {
        Map<String, String> header = new HashMap<>();
        Map<String, String> cookies = new HashMap<>();
        if (userService.login(request.getBody())) {
            header.put("location", "http://" + request.getHeader().get("Host") + "/user/login_failed.html");
            cookies.put("logined", "false");
            return new HttpResponse(HttpStatusCode.FOUND, header, cookies);
        }
        header.put("location", "http://" + request.getHeader().get("Host") + "index.html");
        cookies.put("logined", "true");
        return new HttpResponse(HttpStatusCode.FOUND, header, cookies);
    }
}
