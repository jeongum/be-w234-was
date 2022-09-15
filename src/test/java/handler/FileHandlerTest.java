package handler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.request.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

class FileHandlerTest {


    private FileHandler fileHandler = new FileHandler();

    @Test
    @DisplayName("webapp에 있는 파일을 읽어 response를 만든다.")
    void responseFile(){
        // given
        HttpRequest request = new HttpRequest(HttpMethod.GET, "/index.html");

        // when
        HttpResponse response = fileHandler.responseFile(request);

        // then
        String body = new String(response.getBody());
        assertTrue(body.contains("SLiPP Java Web Programming"));
    }

    @Test
    @DisplayName("webapp에 있는 파일이 없을 경우 default response를 만든다.")
    void responseDefault() {
        // given
        HttpRequest request = new HttpRequest(HttpMethod.GET, "/test.html");

        // when
        HttpResponse response = fileHandler.responseFile(request);

        // then
        String body = new String(response.getBody());
        assertTrue(body.contains("Hello World"));
    }
}