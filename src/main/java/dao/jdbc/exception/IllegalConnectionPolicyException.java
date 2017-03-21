package dao.jdbc.exception;

public class IllegalConnectionPolicyException extends RuntimeException {
    public IllegalConnectionPolicyException() {
        super();
    }

    public IllegalConnectionPolicyException(String message) {
        super(message);
    }

    public IllegalConnectionPolicyException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalConnectionPolicyException(Throwable cause) {
        super(cause);
    }
}
