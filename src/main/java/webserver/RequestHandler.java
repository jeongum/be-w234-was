package webserver;

import handler.Handler;
import handler.HandlerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.*;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final HandlerMapper mapper = new HandlerMapper();
    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest request = HttpRequest.parseHttpRequest(new BufferedReader(new InputStreamReader(in, "UTF-8")));

            Handler handler = mapper.handlerMapping(request.getPath());
            HttpResponse response = handler.handle(request);

            sendHttpResponse(out, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void sendHttpResponse(OutputStream out, HttpResponse response) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        dos.write(response.getHeaderByte());
        dos.write(response.getBody());
        dos.flush();
    }
}
