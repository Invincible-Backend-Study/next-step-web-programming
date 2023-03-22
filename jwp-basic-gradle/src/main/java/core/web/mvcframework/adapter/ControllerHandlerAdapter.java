package core.web.mvcframework.adapter;

import core.web.mvcframework.ModelAndView;
import core.web.mvcframework.controller.Controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllerHandlerAdapter implements HandlerAdapter {

    private Logger log = LoggerFactory.getLogger(ControllerHandlerAdapter.class);

    @Override
    public boolean support(final Object handler) {
        return handler instanceof Controller;
    }

    @Override
    public ModelAndView execute(final HttpServletRequest request, final HttpServletResponse response,
                                final Object handler) {
        log.debug("call ControllerHandlerAdapter = {}, requestUri = {}", handler, request.getRequestURI());
        return ((Controller) handler).execute(request, response);
    }

}
