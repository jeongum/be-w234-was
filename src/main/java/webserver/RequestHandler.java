package webserver;

import controller.HomeController;
import controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final HomeController homeController = new HomeController();
    private static final UserController userController = new UserController();

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest request = new HttpRequest(new BufferedReader(new InputStreamReader(in, "UTF-8")));
            HttpResponse response = null;
            if (request.getPath().contains("/user/create")) {
                response = userController.create(request);
            } else if (request.getPath().contains("/index") || request.getPath().contains("/user/form")) {
                response = homeController.responseFile(request);
            }
            response.generateResponse(out);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}
