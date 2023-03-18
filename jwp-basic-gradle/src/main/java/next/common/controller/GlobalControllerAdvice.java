package next.common.controller;


import core.jdbc.DataAccessException;
import core.mvc.JspView;
import core.mvc.View;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class GlobalControllerAdvice {
    private final View view;

    public GlobalControllerAdvice(DataAccessException accessException) {
        this.view = new JspView("/WEB-INF/user/signup_failed.jsp");
    }

    public static GlobalControllerAdvice process(Exception exception) {
        final var errorException = exception.getClass();
        final var a = exception instanceof DataAccessException ? ((DataAccessException) exception) : null;

        exception.printStackTrace();
        log.error("{}", exception.getMessage());

        if (exception instanceof DataAccessException) {
            return new GlobalControllerAdvice((DataAccessException) exception);
        }
        return null;
    }
}
