package core.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Deprecated
public interface Controller {
    ModelAndView execute(HttpServletRequest request, HttpServletResponse response);
}
