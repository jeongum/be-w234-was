package webserver.http.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import webserver.http.MIME;
import webserver.http.request.HttpMethod;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Getter
@NoArgsConstructor
public class HttpResponse {

    private HttpStatusCode status;
    private Map<String, String> header = new HashMap<>();
    private byte[] body = new byte[0];

    public HttpResponse(MIME mime, byte[] body) {
        this.status = HttpStatusCode.OK;
        this.body = body;
        this.header.put("mime", mime.getMIME());
    }

    public HttpResponse(String host, String location) {
        this.status = HttpStatusCode.FOUND;
        this.header.put("location", "http://" + host + location);
    }

    public byte[] getHeaderByte() {
        StringBuffer sb = new StringBuffer();
        sb.append("HTTP/1.1 " + status.getStatusCode() + " " + status + " \r\n");

        if (status == HttpStatusCode.OK) {
            sb.append("Content-Type: " + header.get("mime") + ";charset=utf-8\r\n");
            sb.append("Content-Length: " + body.length + "\r\n");
            sb.append("\r\n");
        } else if (status == HttpStatusCode.FOUND) {
            String location = header.containsKey("location") ? header.get("location") : "index.html";
            sb.append("Location: " + location);
        }

        return sb.toString().getBytes();
    }
}
