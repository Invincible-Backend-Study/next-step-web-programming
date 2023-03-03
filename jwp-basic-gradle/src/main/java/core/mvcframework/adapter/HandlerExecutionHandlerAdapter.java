package core.mvcframework.adapter;

import core.mvcframework.ModelAndView;
import core.mvcframework.mapping.annotation.HandlerExecution;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HandlerExecutionHandlerAdapter implements HandlerAdapter {

    @Override
    public boolean support(final Object handler) {
        return handler instanceof HandlerExecution;
    }

    @Override
    public ModelAndView execute(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {
        return ((HandlerExecution) handler).handle(request, response);
    }

}
