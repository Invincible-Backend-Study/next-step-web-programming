package next.controller;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.mvcframework.ModelAndView;
import core.mvcframework.controller.AbstractAnnotationController;
import core.mvcframework.controller.AbstractController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.service.QuestionService;

@Controller
public class HomeController extends AbstractAnnotationController {

    private final QuestionService questionService = new QuestionService(new QuestionDao(), new AnswerDao());

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home(final HttpServletRequest request, final HttpServletResponse response) {
        return jspView("home").addObject("questions", questionService.findAllOrderByCreatedDate());
    }

}
