package webserver.http.request;

import webserver.http.HttpBase;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequest extends HttpBase {
    private HttpMethod method;
    private String path;
    private String host;
    private String accept;
    private Connection connection;

    public HttpRequest(HttpMethod method, String path) {
        this.method = method;
        this.path = path;
    }

    public HttpRequest(BufferedReader br) {
        try {
            readHeader(br);
            String[] infos = br.readLine().split(" ");

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void readHeader(BufferedReader br) throws IOException {
        String[] firstLine = br.readLine().split(" ");
        this.method = HttpMethod.valueOf(firstLine[0]);
        this.path = firstLine[1];

        this.host = getNextInfo(br);
        this.accept = getNextInfo(br);
        this.connection = getNextInfo(br).equals("keep-alive") ? Connection.KEEP_ALIVE : Connection.CLOSE;
    }

    private String getNextInfo(BufferedReader br) throws IOException {
        return br.readLine().split(" ")[1];
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }
}
