package webserver.handler.user;

import webserver.handler.Handler;
import service.UserService;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public class UserCreateHandler implements Handler {

    private UserService userService = UserService.getInstance();

    @Override
    public HttpResponse handle(HttpRequest request) {
        userService.createUser(request.getBody());
        return new HttpResponse(request.getHeader().get("Host"), "/index.html");
    }
}
