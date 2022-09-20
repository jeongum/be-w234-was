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
        this.header.put("mime", mime.getMIME());
        this.body = body;
    }

    public HttpResponse(String host, String location) {
        this.status = HttpStatusCode.FOUND;
        this.header.put("location", "http://" + host + location);
    }

    public byte[] getHeaderByte() {
        StringBuffer sb = new StringBuffer();

        // TODO("if-else 줄이기")
        sb.append("HTTP/1.1 " + status.getStatusCode() + " " + status + " \r\n");
        if (status == HttpStatusCode.OK) {
            sb.append("Content-Type: " + header.get("mime") + ";charset=utf-8\r\n");
            sb.append("Content-Length: " + body.length + "\r\n");
        } else if (status == HttpStatusCode.FOUND) {
            sb.append("Location: " + header.get("location"));
        }
        sb.append("\r\n");

        return sb.toString().getBytes();
    }
}
