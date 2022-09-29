package exception;

public enum UserExceptionMessage {
    INVALID_USER_PARAMETER("Invalid Parameter"),
    DUPLICATE_USER("Duplicate User"),
    USER_NOT_FOUND("User Not Found");

    final String message;
    UserExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }
}
