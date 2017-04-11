package util;

public class PropertyLoaderException extends RuntimeException {
    public PropertyLoaderException() {
        super();
    }

    public PropertyLoaderException(String message) {
        super(message);
    }

    public PropertyLoaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public PropertyLoaderException(Throwable cause) {
        super(cause);
    }
}
