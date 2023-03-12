package next.controller.qna.api.legacy;

import core.mvcframework.ModelAndView;
import core.mvcframework.controller.AbstractController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.exception.CannotDeleteQuestionException;
import next.model.Result;
import next.model.User;
import next.service.QuestionService;
import next.utils.SessionUtil;

public class QuestionDeleteApiController extends AbstractController {

    private final QuestionService questionService;

    public QuestionDeleteApiController(final QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public ModelAndView execute(final HttpServletRequest request, final HttpServletResponse response) {
        long questionId = Long.parseLong(request.getParameter("questionId"));
        User user = SessionUtil.getLoginObject(request.getSession(), "user");
        try {
            questionService.deleteQuestion(questionId, user);
            return jsonView().addObject("result", Result.ok());
        } catch (CannotDeleteQuestionException exception) {
            return jsonView().addObject("result", Result.fail(exception.getMessage()));
        }
    }

}
