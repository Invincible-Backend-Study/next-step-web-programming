package core.mvcframework.adapter;

import core.mvcframework.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerAdapter {

    boolean support(Object handler);

    ModelAndView execute(HttpServletRequest request, HttpServletResponse response, Object handler);

}
