package next.exception;

public class CannotUpdateQuestionException extends RuntimeException {

    public CannotUpdateQuestionException(final String message) {
        super(message);
    }
}
