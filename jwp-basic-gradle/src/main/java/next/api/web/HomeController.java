package next.api.web;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.web.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.common.controller.AbstractController;
import next.common.view.JspView;

@Controller
public class HomeController extends AbstractController {
    @Override
    @RequestMapping("/index")
    protected ModelAndView doGet(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(new JspView("redirect:/question/list"));
    }
}
