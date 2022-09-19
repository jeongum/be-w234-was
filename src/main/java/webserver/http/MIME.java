package webserver.http;

public enum MIME {
    CSS("text/css"), HTML("text/html");

    private final String mime;

    MIME(String mime) {
        this.mime = mime;
    }

    public String getMIME() {
        return mime;
    }
}
