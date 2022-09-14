package controller;

import model.User;
import util.HttpRequestUtils;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.util.Map;

public class UserController {

    public HttpResponse create(HttpRequest request) {
        String queryString = request.getPath().split("\\?")[1];
        Map<String, String> params = HttpRequestUtils.parseQueryString(queryString);
        User user = new User(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));

        return new HttpResponse(user.getUserId().getBytes());
    }
}
