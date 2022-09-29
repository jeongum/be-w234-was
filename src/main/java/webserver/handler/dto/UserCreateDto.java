package webserver.handler.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserCreateDto {
    private String userId;
    private String password;
    private String name;
    private String email;
}
