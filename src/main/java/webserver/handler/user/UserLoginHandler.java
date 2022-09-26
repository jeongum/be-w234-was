package webserver.handler.user;

import constant.Path;
import exception.UserException;
import lombok.extern.slf4j.Slf4j;
import service.UserService;
import webserver.handler.Handler;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatusCode;

@Slf4j
public class UserLoginHandler implements Handler {

    private UserService userService = UserService.getInstance();

    @Override
    public HttpResponse handle(HttpRequest request) {
        try {
            boolean login = userService.login(request.getBody());
            String location = (login) ? Path.HOME : Path.LOGIN_FAILED;
            return new HttpResponse(HttpStatusCode.FOUND, HttpResponse.generateLocationHeader(request.getHeader().get("Host"), location), HttpResponse.generateLoginCookie(login));
        } catch (UserException e) {
            return new HttpResponse(HttpStatusCode.BAD_REQUEST, e.getMessage());
        }
    }
}
