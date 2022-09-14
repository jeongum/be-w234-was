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


    public HttpResponse(HttpRequest request) {
        this.body = generateBody(request);
        this.header = generateHeader(body.length);
    }

    private byte[] generateBody(HttpRequest request) {
        try {
            this.status = HttpStatusCode.OK;
            return Files.readAllBytes(new File("./webapp" + request.getPath()).toPath());
        } catch (IOException e) {
            this.status = HttpStatusCode.NOT_FOUND;
            return "Hello World".getBytes();
        }
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
