package core.mvcframework.mapping.annotation;

import core.mvcframework.ModelAndView;
import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HandlerExecution {

    private static final Logger log = LoggerFactory.getLogger(HandlerExecution.class);

    private final Object declaredObject;
    private final Method method;

    public HandlerExecution(final Method method) {
        this.declaredObject = getDeclaredObject(method);
        this.method = method;
    }

    private static Object getDeclaredObject(final Method method) {
        try {
            return method.getDeclaringClass().getConstructor().newInstance();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) {
        try {
            return (ModelAndView) method.invoke(declaredObject, request, response);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new RuntimeException();
        }
    }

}
