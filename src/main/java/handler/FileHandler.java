package handler;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileHandler implements Handler{

    @Override
    public HttpResponse handle(HttpRequest request){
        return responseFile(request);
    }
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
