package util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileUtils {
    public static byte[] getContents(String path) {
        try {
            return Files.readAllBytes(new File("./webapp" + path).toPath());
        } catch (IOException e) {
            return null;
        }
    }
}
