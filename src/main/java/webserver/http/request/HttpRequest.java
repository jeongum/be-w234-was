package webserver.http.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import webserver.http.ContentType;
import webserver.http.HttpBase;

import java.io.BufferedReader;
import java.io.IOException;

@Slf4j
@Getter
@NoArgsConstructor
public class HttpRequest {
    private HttpMethod method;
    private String path;
    private String query;
    private ContentType contentType;
    private String host;
    private String accept;
    private Connection connection;

    public HttpRequest(BufferedReader br) {
        readHeader(br);
    }

    private void readHeader(BufferedReader br) {
        try {
            String[] firstLine = br.readLine().split(" ");
            this.method = HttpMethod.valueOf(firstLine[0]);
            this.path = generatePath(firstLine[1]);
            this.query = generateQuery(firstLine[1]);
            this.contentType = generateContentType(path);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private ContentType generateContentType(String path) {
        if (path.endsWith(".css")) return ContentType.CSS;
        return ContentType.HTML;
    }

    private String generateQuery(String url) {
        String[] query = url.split("\\?");
        return query.length == 1 ? null : query[1];
    }

    private String generatePath(String url) {
        return url.split("\\?")[0];
    }
}
