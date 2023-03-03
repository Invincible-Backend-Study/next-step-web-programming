package core.mvcframework.adapter;

import core.mvcframework.ModelAndView;
import core.mvcframework.mapping.annotation.HandlerExecution;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HandlerExecutionHandlerAdapter implements HandlerAdapter {

    private static final Logger log = LoggerFactory.getLogger(HandlerExecutionHandlerAdapter.class);

    @Override
    public boolean support(final Object handler) {
        return handler instanceof HandlerExecution;
    }

    @Override
    public ModelAndView execute(final HttpServletRequest request, final HttpServletResponse response,
                                final Object handler) {
        log.debug("call HandlerExecutionHandlerAdapter = {}, requestUri = {}", handler, request.getRequestURI());
        return ((HandlerExecution) handler).handle(request, response);
    }

}
