package core.mvcframework.controller;

import core.mvcframework.ModelAndView;
import core.mvcframework.view.View;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller {
    ModelAndView execute(HttpServletRequest request, HttpServletResponse response);
}
