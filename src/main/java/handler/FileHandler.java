package handler;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatusCode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileHandler implements Handler {

    @Override
    public HttpResponse handle(HttpRequest request) {
        return responseFile(request);
    }

    public HttpResponse responseFile(HttpRequest request) {
        try {
            byte[] body = getFileContents(request.getPath());
            return new HttpResponse(HttpStatusCode.OK, request.getContentType(), body);
        } catch (IOException e) {
            return new HttpResponse("Hello World".getBytes());
        }
    }

    public byte[] getFileContents(String path) throws IOException {
        return Files.readAllBytes(new File("./webapp" + path).toPath());
    }

}
