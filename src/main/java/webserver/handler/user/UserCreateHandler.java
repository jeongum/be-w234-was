package webserver.handler.user;

import constant.Path;
import lombok.extern.slf4j.Slf4j;
import webserver.handler.Handler;
import service.UserService;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatusCode;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class UserCreateHandler implements Handler {

    private UserService userService = UserService.getInstance();

    @Override
    public HttpResponse handle(HttpRequest request) {
        Map<String, String> header = new HashMap<>();
        String location;
        try {
            userService.createUser(request.getBody());
            location = Path.HOME;
        } catch (InvalidParameterException e) {
            log.error(e.getMessage());
            location = Path.JOIN;
        }
        header.put("location", Path.HTTP + request.getHeader().get("Host") + location);
        return new HttpResponse(HttpStatusCode.FOUND, header);
    }
}
