package webserver.http;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequest extends HttpBase{
    private HttpMethod method;
    private String path;

    public HttpRequest(BufferedReader br) {
        try {
            String[] infos = br.readLine().split(" ");
            this.method = HttpMethod.valueOf(infos[0]);
            this.path = infos[1];
        } catch (IOException e){
            logger.error(e.getMessage());
        }
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }
}
