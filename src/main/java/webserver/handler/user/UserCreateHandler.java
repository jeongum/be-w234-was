package webserver.handler.user;

import constant.Path;
import webserver.handler.Handler;
import service.UserService;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatusCode;

import java.util.HashMap;
import java.util.Map;

public class UserCreateHandler implements Handler {

    private UserService userService = UserService.getInstance();

    @Override
    public HttpResponse handle(HttpRequest request) {
        Map<String, String> header = new HashMap<>();

        userService.createUser(request.getBody());

        header.put("location", "http://" + request.getHeader().get("Host") + Path.HOME);
        return new HttpResponse(HttpStatusCode.FOUND, header);
    }
}
