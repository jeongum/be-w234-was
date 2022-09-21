package webserver.handler.file;

import webserver.handler.file.FileHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileHandlerTest {

    private FileHandler fileHandler = new FileHandler();

    @Test
    @DisplayName("./webapp에 있는 파일을 읽어 내용을 반환한다.")
    void getContents() {
        // given
        String path = "/index.html";

        // when
        byte[] contents = fileHandler.getContents(path);

        // then
        String contentString = new String(contents);
        assertTrue(contentString.contains("SLiPP Java Web Programming"));
    }
}