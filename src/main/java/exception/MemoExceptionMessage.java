package exception;

public enum MemoExceptionMessage {
    MEMO_CREATE_ERROR("failed creation"),
    INVALID_MEMO_PARAMETER("invalid parameter");

    final String message;
    MemoExceptionMessage(String message) {
        this.message = message;
    }
}
