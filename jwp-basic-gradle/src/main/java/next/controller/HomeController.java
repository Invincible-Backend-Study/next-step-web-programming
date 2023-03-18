package next.controller;

import core.annotation.Controller;
import core.annotation.Inject;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.mvcframework.ModelAndView;
import core.mvcframework.controller.AbstractAnnotationController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.service.QuestionService;

@Controller
public class HomeController extends AbstractAnnotationController {


    private final QuestionService questionService;

    @Inject
    public HomeController(final QuestionService questionService) {
        this.questionService = questionService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home(final HttpServletRequest request, final HttpServletResponse response) {
        return jspView("home").addObject("questions", questionService.findAllOrderByCreatedDate());
    }

}
