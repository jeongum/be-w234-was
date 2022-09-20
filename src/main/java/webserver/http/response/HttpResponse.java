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
    private Map<String, String> cookies = new HashMap<>();
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

    public HttpResponse(String host, String location, Map<String, String> cookies) {
        this(host, location);
        this.cookies.putAll(cookies);
        for (Map.Entry<String, String> entry : cookies.entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue() + "; ");
        }
    }

    public byte[] getHeaderByte() {
        StringBuffer sb = new StringBuffer();

        // TODO("if-else 줄이기")
        sb.append("HTTP/1.1 " + status.getStatusCode() + " " + status + " \r\n");
        if (status == HttpStatusCode.OK) {
            sb.append("Content-Type: " + header.get("mime") + ";charset=utf-8\r\n");
            sb.append("Content-Length: " + body.length + "\r\n");
        } else if (status == HttpStatusCode.FOUND) {
            sb.append("Location: " + header.get("location") + "\r\n");
        }

        for (Map.Entry<String, String> entry : cookies.entrySet()) {
            sb.append("Set-Cookie: ");
            sb.append(entry.getKey() + "=" + entry.getValue() + "; ");
            if (entry.getKey().equals("logined")) {
                sb.append("Path=/");
            }
            sb.append("\r\n");
        }

        sb.append("\r\n");


        return sb.toString().getBytes();
    }
}
