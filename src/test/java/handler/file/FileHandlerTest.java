package handler.file;

import handler.file.FileHandler;
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

    @Test
    @DisplayName("파일이 없을 경우 Default를 반환한다.")
    void getDefaultContents(){
        // given
        String path = "nofile.html";

        // when
        byte[] contents = fileHandler.getContents(path);

        // then
        String contentString = new String(contents);
        assertTrue(contentString.contains("Hello World"));
    }
}