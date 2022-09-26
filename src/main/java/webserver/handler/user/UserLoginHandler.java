package webserver.handler.user;

import constant.Path;
import lombok.extern.slf4j.Slf4j;
import service.UserService;
import webserver.handler.Handler;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatusCode;

import java.security.InvalidParameterException;

@Slf4j
public class UserLoginHandler implements Handler {

    private UserService userService = UserService.getInstance();

    @Override
    public HttpResponse handle(HttpRequest request) {
        boolean logined;
        try {
            logined = userService.login(request.getBody());
        } catch (InvalidParameterException e) {
            logined = false;
        }

        String location = (logined) ? Path.HOME : Path.LOGIN_FAILED;
        return new HttpResponse(HttpStatusCode.FOUND, HttpResponse.generateLocationHeader(request.getHeader().get("Host"), location), HttpResponse.generateLoginCookie(logined));
    }
}
