package dao.exception;

public class IllegalTransactionStateException extends IllegalStateException {
    public IllegalTransactionStateException() {
        super();
    }

    public IllegalTransactionStateException(String s) {
        super(s);
    }

    public IllegalTransactionStateException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalTransactionStateException(Throwable cause) {
        super(cause);
    }
}
