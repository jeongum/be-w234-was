package controller;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class HomeController {

    public HttpResponse responseFile(HttpRequest request) {
        byte[] body;
        try {
            body = Files.readAllBytes(new File("./webapp" + request.getPath()).toPath());
        } catch (IOException e) {
            body = "Hello World".getBytes();
        }
        return new HttpResponse(body);
    }

}
