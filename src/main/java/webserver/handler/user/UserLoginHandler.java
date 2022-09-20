package webserver.handler.user;

import service.UserService;
import webserver.handler.Handler;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.util.HashMap;
import java.util.Map;

public class UserLoginHandler implements Handler {

    private UserService userService = UserService.getInstance();

    @Override
    public HttpResponse handle(HttpRequest request) {
        Map<String, String> cookies = new HashMap<>();
        if (userService.login(request.getBody())) {
            cookies.put("logined", "false");
            return new HttpResponse(request.getHeader().get("Host"), "/user/login_failed.html", cookies);
        }
        cookies.put("logined", "true");
        return new HttpResponse(request.getHeader().get("Host"), "/index.html", cookies);
    }
}
