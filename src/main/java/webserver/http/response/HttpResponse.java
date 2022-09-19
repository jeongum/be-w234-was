package webserver.http.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import webserver.http.ContentType;

@Slf4j
@Getter
@NoArgsConstructor
public class HttpResponse {

    private HttpStatusCode status;
    private ContentType contentType;
    private String header;
    private byte[] body;

    public HttpResponse(byte[] body) {
        this.status = HttpStatusCode.OK;
        this.contentType = ContentType.HTML;
        this.body = body;
        this.header = generateHeader(body.length);
    }

    public HttpResponse(HttpStatusCode httpStatusCode, ContentType contentType, byte[] body) {
        this.status = httpStatusCode;
        this.contentType = contentType;
        this.body = body;
        this.header = generateHeader(body.length);
    }


    private String generateHeader(int contentLength) {
        StringBuffer sb = new StringBuffer();
        sb.append("HTTP/1.1 " + status.getStatusCode() + " " + status + " \r\n");
        sb.append("Content-Type: "+ contentType.getMIME() +";charset=utf-8\r\n");
        sb.append("Content-Length: " + contentLength + "\r\n");
        sb.append("\r\n");
        return sb.toString();
    }
}
