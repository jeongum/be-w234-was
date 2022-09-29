package webserver.handler.user;

import constant.Path;
import exception.UserException;
import exception.UserExceptionMessage;
import lombok.extern.slf4j.Slf4j;
import service.UserService;
import util.DtoConverter;
import webserver.handler.Handler;
import webserver.handler.dto.UserLoginDto;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatusCode;

import java.util.Map;

@Slf4j
public class UserLoginHandler implements Handler {

    private UserService userService = UserService.getInstance();

    @Override
    public HttpResponse handle(HttpRequest request) {
        try {
            UserLoginDto dto = DtoConverter.convertUserLogin(request.getBody());
            String loginUser = userService.login(dto);
            String location = (loginUser != null) ? Path.HOME : Path.LOGIN_FAILED;
            return new HttpResponse(HttpStatusCode.FOUND, HttpResponse.generateLocationHeader(request.getHeader().get("Host"), location), HttpResponse.generateLoginCookie(loginUser));
        } catch (UserException e) {
            return new HttpResponse(HttpStatusCode.BAD_REQUEST, e.getMessage());
        }
    }
}
