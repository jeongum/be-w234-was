package webserver.handler.memo;

import constant.Path;
import exception.MemoException;
import exception.MemoExceptionMessage;
import exception.UserException;
import service.MemoService;
import util.DtoConverter;
import webserver.handler.Handler;
import webserver.handler.dto.MemoCreateDto;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatusCode;

import java.util.Map;

public class MemoCreateHandler implements Handler {

    private MemoService memoService = MemoService.getInstance();

    @Override
    public HttpResponse handle(HttpRequest request) {
        if (request.isLogin()) {
            try {
                MemoCreateDto dto = DtoConverter.convertMemoCreate(request.getBody());
                memoService.create(request.getCookie().get("userId"), dto);
                return new HttpResponse(HttpStatusCode.FOUND, HttpResponse.generateLocationHeader(request.getHeader().get("Host"), Path.MEMO_LIST));
            } catch (MemoException e) {
                return new HttpResponse(HttpStatusCode.BAD_REQUEST, e.getMessage());
            }
        }
        return new HttpResponse(HttpStatusCode.FOUND, HttpResponse.generateLocationHeader(request.getHeader().get("Host"), Path.LOGIN));
    }
}
