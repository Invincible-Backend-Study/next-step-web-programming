package next.controller.qna.legacy;

import core.mvcframework.ModelAndView;
import core.mvcframework.controller.AbstractController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.exception.CannotDeleteQuestionException;
import next.model.User;
import next.service.QuestionService;
import next.utils.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuestionDeleteController extends AbstractController {

    private static final Logger log = LoggerFactory.getLogger(QuestionDeleteController.class);

    private final QuestionService questionService;

    public QuestionDeleteController(final QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public ModelAndView execute(final HttpServletRequest request, final HttpServletResponse response) {
        long questionId = Long.parseLong(request.getParameter("questionId"));
        User user = SessionUtil.getLoginObject(request.getSession(), "user");
        try {
            questionService.deleteQuestion(questionId, user);
            return jspView("redirect:/");
        } catch (CannotDeleteQuestionException exception) {
            log.debug("cannotDeleteQuestion={}", exception.getMessage());
            return jspView("qna/show").addObject(
                            "questionWithAnswers",
                            questionService.findByQuestionIdWithAnswers(questionId))
                    .addObject("errorMessage", exception.getMessage());
        }
    }

}
