package util.init;

public class InitializerException extends RuntimeException {
    public InitializerException() {
        super();
    }

    public InitializerException(String message) {
        super(message);
    }

    public InitializerException(String message, Throwable cause) {
        super(message, cause);
    }

    public InitializerException(Throwable cause) {
        super(cause);
    }
}
