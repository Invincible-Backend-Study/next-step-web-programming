package next.exception;

public class CannotDeleteQuestionException extends RuntimeException {

    public CannotDeleteQuestionException(final String message) {
        super(message);
    }
}
