package http.exception;

public class HttpException extends RuntimeException {
    private final int code;
    private final String exceptionMessage;

    public HttpException(int code, String exceptionMessage) {
        super(exceptionMessage);
        this.code = code;
        this.exceptionMessage = exceptionMessage;
    }

    public HttpException(String message, int code, String exceptionMessage) {
        super(message);
        this.code = code;
        this.exceptionMessage = exceptionMessage;
    }

    public HttpException(String message, Throwable cause, int code, String exceptionMessage) {
        super(message, cause);
        this.code = code;
        this.exceptionMessage = exceptionMessage;
    }

    public HttpException(Throwable cause, int code, String exceptionMessage) {
        super(cause);
        this.code = code;
        this.exceptionMessage = exceptionMessage;
    }

    public HttpException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
                         int code,
                         String exceptionMessage) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
        this.exceptionMessage = exceptionMessage;
    }
}
