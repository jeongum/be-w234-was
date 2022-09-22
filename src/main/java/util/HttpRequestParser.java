package util;

import lombok.extern.slf4j.Slf4j;
import webserver.http.MIME;
import webserver.http.request.HttpMethod;
import webserver.http.request.HttpRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class HttpRequestParser {
    public static HttpRequest parse(BufferedReader br) throws IOException {
        Map<String, String> parameter = new HashMap<>();
        Map<String, String> header = new HashMap<>();
        Map<String, String> cookie = new HashMap<>();
        Map<String, String> body = new HashMap<>();

        // Set First Info
        String[] firstLine = br.readLine().split(" ");
        HttpMethod method = HttpMethod.valueOf(firstLine[0]);

        String[] url = firstLine[1].split("\\?");
        String path = url[0];
        if (url.length > 1) {
            parameter = parseKeyValue(url[1]);
        }
        MIME mime = MIME.getMIMEFromPath(path);


        // Set Header
        String line;
        while (true) {
            line = br.readLine();
            if (line == null || "".equals(line)) break;
            HttpRequestUtils.Pair pair = generateHeaderPair(line);
            if ("Cookie".equals(pair.getKey())) {
                cookie = HttpRequestUtils.parseCookies(pair.getValue());
                continue;
            }
            header.put(pair.getKey(), pair.getValue());
        }

        // Set Body
        if (header.containsKey("Content-Length")) {
            String bodyStr = IOUtils.readData(br, Integer.parseInt(header.get("Content-Length")));
            body = parseKeyValue(bodyStr);
        }

        return new HttpRequest(method, path, mime, header, body, parameter, cookie);
    }

    private static Map<String, String> parseKeyValue(String string) {
        return HttpRequestUtils.parseQueryString(string);
    }

    private static HttpRequestUtils.Pair generateHeaderPair(String line) {
        return HttpRequestUtils.parseHeader(line);
    }

}
