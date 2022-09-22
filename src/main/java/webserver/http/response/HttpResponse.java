package webserver.http.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import webserver.http.MIME;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Getter
public class HttpResponse {

    private HttpStatusCode status;
    private Map<String, String> header = new HashMap<>();
    private Map<String, String> cookies = new HashMap<>();
    private byte[] body = new byte[0];

    public HttpResponse(HttpStatusCode status, Map<String, String> header) {
        this.status = status;
        this.header.putAll(header);
    }

    public HttpResponse(HttpStatusCode status, Map<String, String> header, byte[] body) {
        this(status, header);
        this.body = body;
    }

    public HttpResponse(HttpStatusCode status, Map<String, String> header, Map<String, String> cookies) {
        this(status, header);
        this.cookies.putAll(cookies);
    }

    public byte[] getHeaderByte() {
        StringBuilder sb = new StringBuilder();

        sb.append("HTTP/1.1 " + status.getStatusCode() + " " + status + " \r\n");

        switch (status) {
            case OK: {
                sb.append("Content-Type: " + header.get("mime") + ";charset=utf-8\r\n");
                sb.append("Content-Length: " + body.length + "\r\n");
                break;
            }
            case FOUND: {
                sb.append("Location: " + header.get("location") + "\r\n");
                break;
            }
        }

        sb.append(generateCookieHeader());
        sb.append("\r\n");

        return sb.toString().getBytes();
    }

    private String generateCookieHeader() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : cookies.entrySet()) {
            sb.append("Set-Cookie: ");
            sb.append(entry.getKey() + "=" + entry.getValue() + "; ");
            sb.append("Path=/");
            sb.append("\r\n");
        }
        return sb.toString();
    }

}
