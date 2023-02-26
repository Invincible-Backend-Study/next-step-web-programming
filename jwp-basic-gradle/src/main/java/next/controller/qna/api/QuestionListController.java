package next.controller.qna.api;

import core.mvcframework.ModelAndView;
import core.mvcframework.controller.AbstractController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.service.QuestionService;

public class QuestionListController extends AbstractController {

    private final QuestionService questionService = new QuestionService();

    @Override
    public ModelAndView execute(final HttpServletRequest request, final HttpServletResponse response) {
        return jsonView().addObject("questions", questionService.findAll());
    }
}
