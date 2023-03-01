package next.common.controller;

import core.web.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.common.view.JspView;

public class HomeController extends AbstractController {
    @Override
    protected ModelAndView doGet(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(new JspView("redirect:/question/list"));
    }
}
