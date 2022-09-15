package webserver.http;

import org.checkerframework.checker.units.qual.C;

public enum ContentType {
    CSS("text/css"), HTML("text/html");

    private final String mime;

    ContentType(String mime) {
        this.mime = mime;
    }

    public String getMIME() {
        return mime;
    }
}
