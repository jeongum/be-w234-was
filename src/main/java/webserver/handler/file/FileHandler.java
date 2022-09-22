package webserver.handler.file;

import constant.Path;
import util.FileUtils;
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
        Map<String, String> header = new HashMap<>();

        byte[] body = FileUtils.getContents(request.getPath());

        if (body == null) {
            header.put("location", "http://" + request.getHeader().get("Host") + Path.HOME);
            return new HttpResponse(HttpStatusCode.FOUND, header);
        }

        header.put("mime", request.getMime().getValue());
        return new HttpResponse(HttpStatusCode.OK, header, body);
    }
}
