package webserver.handler.user;

import constant.Path;
import exception.UserException;
import exception.UserExceptionMessage;
import lombok.extern.slf4j.Slf4j;
import service.UserService;
import util.DtoConverter;
import webserver.handler.Handler;
import webserver.handler.dto.UserCreateDto;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatusCode;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class UserCreateHandler implements Handler {

    private UserService userService = UserService.getInstance();

    @Override
    public HttpResponse handle(HttpRequest request) {
        try {
            UserCreateDto dto = DtoConverter.convertUserCreate(request.getBody());
            userService.createUser(dto);
            return new HttpResponse(HttpStatusCode.FOUND, HttpResponse.generateLocationHeader(request.getHeader().get("Host"), Path.HOME));
        } catch (UserException e) {
            return new HttpResponse(HttpStatusCode.BAD_REQUEST, e.getMessage());
        }
    }
}
