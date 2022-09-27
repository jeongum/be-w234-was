package webserver.handler.memo;

import constant.Path;
import exception.MemoException;
import exception.UserException;
import service.MemoService;
import webserver.handler.Handler;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatusCode;

public class MemoCreateHandler implements Handler {

    private MemoService memoService = MemoService.getInstance();

    @Override
    public HttpResponse handle(HttpRequest request) {
        if (request.isLogin()) {
            try {
                memoService.create(request.getCookie().get("userId"), request.getBody());
                return new HttpResponse(HttpStatusCode.FOUND, HttpResponse.generateLocationHeader(request.getHeader().get("Host"), Path.MEMO_LIST));
            } catch (UserException | MemoException e) {
                return new HttpResponse(HttpStatusCode.BAD_REQUEST, e.getMessage());
            }
        }
        return new HttpResponse(HttpStatusCode.FOUND, HttpResponse.generateLocationHeader(request.getHeader().get("Host"), Path.LOGIN));
    }
}
