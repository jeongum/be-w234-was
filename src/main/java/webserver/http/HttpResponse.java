package webserver.http;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class HttpResponse extends HttpBase{

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

    public void generateResponse(OutputStream out) {
        try {
            DataOutputStream dos = new DataOutputStream(out);
            dos.writeBytes(header);
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e){
            logger.error(e.getMessage());
        }
    }
}
