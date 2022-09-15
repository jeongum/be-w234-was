package handler.user;

import handler.Handler;
import model.User;
import util.HttpRequestUtils;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.util.Map;

public class UserCreateHandler implements Handler {

    @Override
    public HttpResponse handle(HttpRequest request) {
        Map<String, String> params = HttpRequestUtils.parseQueryString(request.getQuery());

        User user = createUser(params);

        return new HttpResponse(user.getUserId().getBytes());
    }

    public User createUser(Map<String, String> params) {
        User user = new User(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));
        return user;
    }
}
