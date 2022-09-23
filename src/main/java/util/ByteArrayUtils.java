package util;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Objects;

public class ByteArrayUtils {
    public static byte[] concat(byte[]... arrays) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        if (arrays != null) {
            Arrays.stream(arrays).filter(Objects::nonNull)
                    .forEach(array -> out.write(array, 0, array.length));
        }
        return out.toByteArray();
    }
}
