package core.nmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.web.Controller;
import core.web.ModelAndView;

import java.lang.reflect.Method;

public class HandlerExecution {
    private final Controller controller;
    private final Method method;

    public HandlerExecution(Controller controller, Method method) {
        this.controller = controller;
        this.method = method;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return null;
    }
}
