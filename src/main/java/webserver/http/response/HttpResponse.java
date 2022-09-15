package webserver.http.response;

import webserver.http.ContentType;
import webserver.http.HttpBase;

public class HttpResponse extends HttpBase {

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


    private String generateHeader(int lengthOfBodyContent) {
        StringBuffer sb = new StringBuffer();
        sb.append("HTTP/1.1 " + status.getStatusCode() + " " + status + " \r\n");
        sb.append("Content-Type: "+ contentType.getMIME() +";charset=utf-8\r\n");
        sb.append("Content-Length: " + lengthOfBodyContent + "\r\n");
        sb.append("\r\n");
        return sb.toString();
    }

    public HttpStatusCode getStatus() {
        return status;
    }

    public String getHeader() {
        return header;
    }

    public byte[] getBody() {
        return body;
    }

}
