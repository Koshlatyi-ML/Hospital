package util;

public class LoadInstanceException extends RuntimeException {
    public LoadInstanceException() {
        super();
    }

    public LoadInstanceException(String message) {
        super(message);
    }

    public LoadInstanceException(Throwable cause) {
        super(cause);
    }

    public LoadInstanceException(String message, Throwable cause) {
        super(message, cause);
    }
}
