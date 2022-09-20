package webserver.handler.file;

import webserver.handler.Handler;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatusCode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class FileHandler implements Handler {

    @Override
    public HttpResponse handle(HttpRequest request) {
        byte[] body = getContents(request.getPath());

        Map<String, String> header = new HashMap<>();
        header.put("mime", request.getMime().getMIME());
        return new HttpResponse(HttpStatusCode.OK, header, body);
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
