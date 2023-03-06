package core.rc;

import core.mvc.ModelAndView;
import core.rc.filter.ExecutionRunner;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class HandlerExecution {
    private final Object declaredObject;
    private final Method method;
    private final ExecutionMapper executionMapper = ExecutionMapper.getInstance();


    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) {
        try {
            final var result = ExecutionRunner.getInstance().execute(method, request, response);
            if (!result) {
                return null;
            }
            return (ModelAndView) method.invoke(declaredObject, executionMapper.doMapping(method, request, response));
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }
}
