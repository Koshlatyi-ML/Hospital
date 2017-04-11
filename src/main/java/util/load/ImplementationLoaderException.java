package util.load;

public class ImplementationLoaderException extends RuntimeException {
    public ImplementationLoaderException() {
        super();
    }

    public ImplementationLoaderException(String message) {
        super(message);
    }

    public ImplementationLoaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImplementationLoaderException(Throwable cause) {
        super(cause);
    }
}
