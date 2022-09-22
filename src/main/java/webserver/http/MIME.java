package webserver.http;

public enum MIME {
    CSS("text/css"), HTML("text/html");

    private final String mime;

    MIME(String mime) {
        this.mime = mime;
    }

    public String getValue() {
        return mime;
    }

    public static MIME getMIMEFromPath(String path){
        if (path.endsWith(".css")) return MIME.CSS;
        return MIME.HTML;
    }
}
