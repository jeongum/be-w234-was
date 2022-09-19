package webserver.http.request;

import util.HttpRequestUtils;
import util.IOUtils;
import webserver.http.MIME;
import webserver.http.response.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestParser {
    public static HttpRequest generateHttpRequest(BufferedReader br) throws IOException {
        HttpMethod method = null;
        String path = null;
        MIME mime = null;
        Map<String, String> header = new HashMap<>();
        Map<String, String> body = new HashMap<>();
        Map<String, String> parameter = new HashMap<>();

        // Set First Info
        String firstLine = br.readLine();
        String[] infos = firstLine.split(" ");
        method = HttpMethod.valueOf(infos[0]);
        String[] url = infos[1].split("\\?");
        path = url[0];
        parameter = new HashMap<>();
        if (path.length() > 1) {
            parameter = HttpRequestUtils.parseQueryString(url[1]);
        }
        mime = generateMIME(path);

        // Set Header
        String line = null;
        while(true){
            line = br.readLine();
            if(line == null || "".equals(line)) break;
            HttpRequestUtils.Pair pair = HttpRequestUtils.parseHeader(line);
            header.put(pair.getKey(), pair.getValue());
        }

        // Set Body
        if(header.containsKey("Content-Length")){
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
