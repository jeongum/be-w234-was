package handler;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public interface Handler {
    public HttpResponse handle(HttpRequest request);
}
