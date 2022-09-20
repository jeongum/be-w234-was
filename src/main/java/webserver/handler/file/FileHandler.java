package webserver.handler.file;

import webserver.handler.Handler;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileHandler implements Handler {

    @Override
    public HttpResponse handle(HttpRequest request) {
        byte[] body = getContents(request.getPath());

        return new HttpResponse(request.getMime(), body);
    }

    // TODO("error 처리")
    public byte[] getContents(String path) {
        try {
            return Files.readAllBytes(new File("./webapp" + path).toPath());
        } catch (IOException e) {
            return "Hello World".getBytes();
        }
    }
}
