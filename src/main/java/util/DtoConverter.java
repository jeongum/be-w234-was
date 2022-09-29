package util;

import exception.MemoException;
import exception.MemoExceptionMessage;
import exception.UserException;
import exception.UserExceptionMessage;
import webserver.handler.dto.MemoCreateDto;
import webserver.handler.dto.UserCreateDto;
import webserver.handler.dto.UserLoginDto;

import java.util.Map;

public class DtoConverter {
    public static MemoCreateDto convertMemoCreate(Map<String, String> body){
        if (!(body.containsKey("contents"))) {
            throw new MemoException(MemoExceptionMessage.INVALID_MEMO_PARAMETER);
        }
        return MemoCreateDto.builder()
                .contents(body.get("contents")).build();
    }

    public static UserCreateDto convertUserCreate(Map<String, String> body) {
        if (!(body.containsKey("userId") && body.containsKey("password") && body.containsKey("name") && body.containsKey("email"))) {
            throw new UserException(UserExceptionMessage.INVALID_USER_PARAMETER);
        }

        return UserCreateDto.builder()
                .userId(body.get("userId"))
                .password(body.get("password"))
                .name(body.get("name"))
                .email(body.get("email")).build();
    }

    public static UserLoginDto convertUserLogin(Map<String, String> body) {
        if (!(body.containsKey("userId") && body.containsKey("password"))) {
            throw new UserException(UserExceptionMessage.INVALID_USER_PARAMETER);
        }
        return UserLoginDto.builder()
                .userId(body.get("userId"))
                .password(body.get("password"))
                .build();
    }
}
