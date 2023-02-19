package next.controller;

import core.mvcframework.ModelAndView;
import core.mvcframework.controller.AbstractController;
import core.mvcframework.controller.Controller;
import core.mvcframework.view.JspView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JPanel;
import next.service.QuestionService;

public class HomeController extends AbstractController {
    private final QuestionService questionService = new QuestionService();

    @Override
    public ModelAndView execute(final HttpServletRequest request, final HttpServletResponse response) {
        return jspView("home").addObject("questions", questionService.findAllOrderByCreatedDate());
    }
}
