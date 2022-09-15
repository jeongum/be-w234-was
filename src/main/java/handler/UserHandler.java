package handler;

import model.User;
import util.HttpRequestUtils;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.util.Map;

public class UserHandler implements Handler{


    @Override
    public HttpResponse handle(HttpRequest request){
        if(request.getPath().contains("/create")) return create(request);
        return new HttpResponse("응답을 찾을 수 없습니다".getBytes());
    }
    public HttpResponse create(HttpRequest request) {
        Map<String, String> params = HttpRequestUtils.parseQueryString(request.getQuery());
        User user = new User(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));

        return new HttpResponse(user.getUserId().getBytes());
    }
}
