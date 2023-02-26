package next.controller;

import core.mvcframework.ModelAndView;
import core.mvcframework.controller.AbstractController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.service.QuestionService;

public class HomeController extends AbstractController {

    private final QuestionService questionService = new QuestionService();

    @Override
    public ModelAndView execute(final HttpServletRequest request, final HttpServletResponse response) {
        return jspView("home").addObject("questions", questionService.findAllOrderByCreatedDate());
    }

}
