package util.load;

public class NoDefaultConstructorException extends RuntimeException {
    public NoDefaultConstructorException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoDefaultConstructorException(Throwable cause) {
        super(cause);
    }

    public NoDefaultConstructorException() {
        super();
    }

    public NoDefaultConstructorException(String message) {
        super(message);
    }
}
