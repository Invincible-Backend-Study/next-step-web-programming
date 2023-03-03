package core.nmvc;

import core.web.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HandlerExecution {
    private final Class<?> controller;
    private final Method method;

    public HandlerExecution(Class<?> controller, Method method) {
        this.controller = controller;
        this.method = method;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) {
        try {
            method.setAccessible(true);  //TODO 현재 abstractController 구현으로 인해 method가 protected로 되어있음! public으로 변경되면 해당라인 삭제
            return (ModelAndView) method.invoke(controller.newInstance(), request, response);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            //
        }
        return null;
    }
}
