package util;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.MIME;
import webserver.http.request.HttpMethod;
import webserver.http.request.HttpRequest;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class HttpRequestParserTest {

    @Test
    @DisplayName("HTTP Request를 객체에 파싱한다.")
    void parseHttpRequest() throws IOException {
        // given
        String httpRequest = "GET /index.html HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n";
        InputStream in = new ByteArrayInputStream(httpRequest.getBytes());

        // when
        HttpRequest request = HttpRequestParser.parseHttpRequest(new BufferedReader(new InputStreamReader(in, "UTF-8")));

        // then
        assertEquals(request.getMethod(), HttpMethod.GET);
        assertEquals(request.getPath(), "/index.html");
        assertEquals(request.getMime(), MIME.HTML);
        assertEquals(request.getHeader().get("Connection"), "keep-alive");
    }
}