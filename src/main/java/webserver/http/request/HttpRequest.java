package webserver.http.request;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import webserver.http.MIME;

import java.util.Map;

@Slf4j
@Getter
public class HttpRequest {
    private final HttpMethod method;
    private final String path;
    private final MIME mime;
    private final Map<String, String> header;
    private final Map<String, String> body;
    private final Map<String, String> parameter;


    public HttpRequest(HttpMethod method, String path, MIME mime, Map<String, String> header, Map<String, String> body, Map<String, String> parameter) {
        this.method = method;
        this.path = path;
        this.mime = mime;
        this.header = header;
        this.body = body;
        this.parameter = parameter;
    }
}
