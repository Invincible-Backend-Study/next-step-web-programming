package next.controller.qna.api.legacy;

import core.mvcframework.ModelAndView;
import core.mvcframework.controller.AbstractController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.service.QuestionService;

public class QuestionListApiController extends AbstractController {

    private final QuestionService questionService;

    public QuestionListApiController(final QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public ModelAndView execute(final HttpServletRequest request, final HttpServletResponse response) {
        return jsonView().addObject("questions", questionService.findAll());
    }

}
