package webserver.http.response;

public enum HttpStatusCode {
    OK(200), NOT_FOUND(404), FOUND(302), BAD_REQUEST(400);

    private final int statusCode;

    HttpStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
