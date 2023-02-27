package next.exception;


public class DataAcessException extends RuntimeException{

    public DataAcessException() {
        super();
    }

    public DataAcessException(String message) {
        super(message);
    }

    public DataAcessException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataAcessException(Throwable cause) {
        super(cause);
    }

    protected DataAcessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
