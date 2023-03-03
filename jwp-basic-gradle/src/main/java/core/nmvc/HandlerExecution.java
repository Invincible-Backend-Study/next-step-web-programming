package core.nmvc;

import core.mvcframework.ModelAndView;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HandlerExecution {

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

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return (ModelAndView) method.invoke(declaredObject, request, response);
    }
}
