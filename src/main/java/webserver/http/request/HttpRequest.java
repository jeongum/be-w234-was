package webserver.http.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import webserver.http.MIME;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Getter
@NoArgsConstructor
public class HttpRequest {
    private HttpMethod method;
    private String path;
    private MIME mime;
    private Map<String, String> header;
    private Map<String, String> body;
    private Map<String, String> parameter;

    public HttpRequest(HttpMethod method, String path, MIME mime, Map<String, String> header, Map<String, String> body, Map<String, String> parameter) {
        this.method = method;
        this.path = path;
        this.mime = mime;
        this.header = header;
        this.body = body;
        this.parameter = parameter;
    }
}
