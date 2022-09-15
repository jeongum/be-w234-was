package webserver.http.request;

import webserver.http.ContentType;
import webserver.http.HttpBase;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequest extends HttpBase {
    private HttpMethod method;
    private String path;
    private String query;
    private ContentType contentType;
    private String host;
    private String accept;
    private Connection connection;

    public HttpRequest(HttpMethod method, String path) {
        this.method = method;
        this.path = path;
    }


    public HttpRequest(BufferedReader br) {
        readHeader(br);
    }

    private void readHeader(BufferedReader br) {
        try {
            String[] firstLine = getNextInfo(br);
            this.method = HttpMethod.valueOf(firstLine[0]);
            this.path = generatePath(firstLine[1]);
            this.query = generateQuery(firstLine[1]);
            this.contentType = generateContentType(path);
        } catch (IOException e) {
            logger.error(e.getMessage());
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

    private String[] getNextInfo(BufferedReader br) throws IOException {
        return br.readLine().split(" ");
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getQuery() {
        return query;
    }

    public ContentType getContentType() {
        return contentType;
    }

}
