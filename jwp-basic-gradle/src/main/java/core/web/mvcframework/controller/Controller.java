package core.web.mvcframework.controller;

import core.web.mvcframework.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller {

    ModelAndView execute(HttpServletRequest request, HttpServletResponse response);

}
