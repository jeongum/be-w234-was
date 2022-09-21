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
        try {
            userService.createUser(request.getBody());

            header.put("location", "http://" + request.getHeader().get("Host") + Path.HOME);
            return new HttpResponse(HttpStatusCode.FOUND, header);

        } catch (InvalidParameterException e) {
            log.error(e.getMessage());
            header.put("location", "http://" + request.getHeader().get("Host") + Path.JOIN);
            return new HttpResponse(HttpStatusCode.FOUND, header);
        }
    }
}
