package webserver.http.response;

import webserver.http.HttpBase;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HttpResponse extends HttpBase {

    private HttpStatusCode status;
    private String header;
    private byte[] body;

    public HttpStatusCode getStatus() {
        return status;
    }

    public String getHeader() {
        return header;
    }

    public byte[] getBody() {
        return body;
    }

    public HttpResponse(byte[] body) {
        this.status = HttpStatusCode.OK;
        this.body = body;
        this.header = generateHeader(body.length);
    }


    private String generateHeader(int lengthOfBodyContent) {
        StringBuffer sb = new StringBuffer();
        sb.append("HTTP/1.1 " + status.getStatusCode() + " " + status + " \r\n");
        sb.append("Content-Type: text/html;charset=utf-8\r\n");
        sb.append("Content-Length: " + lengthOfBodyContent + "\r\n");
        sb.append("\r\n");

        return sb.toString();
    }
}
