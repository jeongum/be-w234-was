package webserver.handler.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserLoginDto {
    private String userId;
    private String password;
}
