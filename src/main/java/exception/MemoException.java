package exception;

public class MemoException extends RuntimeException{
    public MemoException(MemoExceptionMessage memoException){
        super(memoException.message);
    }
}
