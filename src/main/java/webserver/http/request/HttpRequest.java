package webserver.http.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import util.HttpRequestUtils;
import util.IOUtils;
import webserver.http.MIME;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
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

    // TODO("역할 분리")
    public static HttpRequest parseHttpRequest(BufferedReader br) throws IOException {
        HttpMethod method;
        String path;
        MIME mime;
        Map<String, String> header = new HashMap<>();
        Map<String, String> body = new HashMap<>();
        Map<String, String> parameter = new HashMap<>();

        // Set First Info
        String firstLine = br.readLine();
        String[] infos = firstLine.split(" ");
        method = HttpMethod.valueOf(infos[0]);
        String[] url = infos[1].split("\\?");
        path = url[0];
        if (url.length > 1) {
            parameter = HttpRequestUtils.parseQueryString(url[1]);
        }
        mime = generateMIME(path);

        // Set Header
        String line;
        while (true) {
            line = br.readLine();
            if (line == null || "".equals(line)) break;
            HttpRequestUtils.Pair pair = HttpRequestUtils.parseHeader(line);
            header.put(pair.getKey(), pair.getValue());
        }

        // Set Body
        if (header.containsKey("Content-Length")) {
            String bodyString = IOUtils.readData(br, Integer.parseInt(header.get("Content-Length")));
            body = HttpRequestUtils.parseQueryString(bodyString);
        }

        return new HttpRequest(method, path, mime, header, body, parameter);
    }

    private static MIME generateMIME(String path) {
        if (path.endsWith(".css")) return MIME.CSS;
        return MIME.HTML;
    }
}
