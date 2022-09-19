package webserver.http.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import webserver.http.MIME;

@Slf4j
@Getter
@NoArgsConstructor
public class HttpResponse {

    private HttpStatusCode status;
    private MIME mime;
    private String header;
    private byte[] body;

    public HttpResponse(byte[] body) {
        this.status = HttpStatusCode.OK;
        this.mime = mime.HTML;
        this.body = body;
        this.header = generateHeader(body.length);
    }

    public HttpResponse(HttpStatusCode httpStatusCode, MIME mime, byte[] body) {
        this.status = httpStatusCode;
        this.mime = mime;
        this.body = body;
        this.header = generateHeader(body.length);
    }


    private String generateHeader(int contentLength) {
        StringBuffer sb = new StringBuffer();
        sb.append("HTTP/1.1 " + status.getStatusCode() + " " + status + " \r\n");
        sb.append("Content-Type: "+ mime.getMIME() +";charset=utf-8\r\n");
        sb.append("Content-Length: " + contentLength + "\r\n");
        sb.append("\r\n");
        return sb.toString();
    }
}
