package controller.exception;

public class ConrollerException extends RuntimeException {
    public ConrollerException() {
        super();
    }

    public ConrollerException(String message) {
        super(message);
    }

    public ConrollerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConrollerException(Throwable cause) {
        super(cause);
    }
}
