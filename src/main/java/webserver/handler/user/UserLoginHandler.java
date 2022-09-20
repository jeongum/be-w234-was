package webserver.handler.user;

import service.UserService;
import webserver.handler.Handler;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public class UserLoginHandler implements Handler {

    private UserService userService = UserService.getInstance();

    @Override
    public HttpResponse handle(HttpRequest request) {
        if(userService.login(request.getBody())){
            return new HttpResponse(request.getHeader().get("Host"), "/index.html");
        }
        return new HttpResponse(request.getHeader().get("Host"), "/user/login_failed.html");
    }
}
