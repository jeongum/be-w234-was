package handler.user;

import handler.Handler;
import model.User;
import service.UserService;
import util.HttpRequestUtils;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatusCode;

import java.security.InvalidParameterException;
import java.util.Map;

public class UserCreateHandler implements Handler {

    private UserService userService = UserService.getInstance();

    @Override
    public HttpResponse handle(HttpRequest request) {
        userService.createUser(request.getBody());
        return new HttpResponse(request.getHeader().get("Host"), "/index.html");
    }
}
