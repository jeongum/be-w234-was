package webserver.http.request;

import lombok.Getter;
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

    public static HttpRequest parseHttpRequest(BufferedReader br) throws IOException {
        HttpMethod method;
        String path;
        MIME mime;
        Map<String, String> parameter;
        Map<String, String> header = new HashMap<>();
        Map<String, String> body = new HashMap<>();

        // Set First Info
        String firstLine = br.readLine();
        method = generateMethod(firstLine);
        path = generatePath(firstLine);
        parameter = generateParameter(firstLine);
        mime = generateMIME(path);

        // Set Header
        String line;
        while (true) {
            line = br.readLine();
            if (line == null || "".equals(line)) break;
            HttpRequestUtils.Pair pair = generateHeaderPair(line);
            header.put(pair.getKey(), pair.getValue());
        }

        // Set Body
        if (header.containsKey("Content-Length")) {
            body = generateBody(br, Integer.parseInt(header.get("Content-Length")));
        }

        return new HttpRequest(method, path, mime, header, body, parameter);
    }

    private static Map<String, String> generateBody(BufferedReader br, int contentLength) {
        try {
            String body = IOUtils.readData(br, contentLength);
            return HttpRequestUtils.parseQueryString(body);
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    private static HttpRequestUtils.Pair generateHeaderPair(String line) {
        return HttpRequestUtils.parseHeader(line);
    }

    private static Map<String, String> generateParameter(String firstLine) {
        Map<String, String> parameter = new HashMap<>();
        String[] url = firstLine.split(" ")[1].split("\\?");
        if (url.length > 1) {
            parameter = HttpRequestUtils.parseQueryString(url[1]);
        }
        return parameter;
    }

    private static String generatePath(String firstLine) {
        String[] url = firstLine.split(" ")[1].split("\\?");
        return url[0];
    }

    private static HttpMethod generateMethod(String firstLine) {
        return HttpMethod.valueOf(firstLine.split(" ")[0]);
    }

    private static MIME generateMIME(String path) {
        if (path.endsWith(".css")) return MIME.CSS;
        return MIME.HTML;
    }
}
