package webserver.handler.dto;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemoCreateDto {
    private String contents;
}
