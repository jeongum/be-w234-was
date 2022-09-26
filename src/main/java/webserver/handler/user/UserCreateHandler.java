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
public class UserCreateHandler implements Handler {

    private UserService userService = UserService.getInstance();

    @Override
    public HttpResponse handle(HttpRequest request) {
        String location;
        try {
            userService.createUser(request.getBody());
            location = Path.HOME;
        } catch (InvalidParameterException e) {
            log.error(e.getMessage());
            location = Path.JOIN;
        }
        return new HttpResponse(HttpStatusCode.FOUND, HttpResponse.generateLocationHeader(request.getHeader().get("Host"), location));
    }
}
