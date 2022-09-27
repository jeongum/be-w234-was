package exception;

public enum MemoExceptionMessage {
    MEMO_CREATE_ERROR("failed creation");

    final String message;
    MemoExceptionMessage(String message) {
        this.message = message;
    }
}
