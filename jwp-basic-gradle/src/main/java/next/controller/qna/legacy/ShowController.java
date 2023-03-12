package next.controller.qna.legacy;

import core.mvcframework.ModelAndView;
import core.mvcframework.controller.AbstractController;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShowController extends AbstractController {

    private static final Logger log = LoggerFactory.getLogger(ShowController.class);

    private final QuestionService questionService;

    public ShowController(final QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public ModelAndView execute(final HttpServletRequest request, final HttpServletResponse response) {
        long questionId = Long.parseLong(request.getParameter("questionId"));
        Map<String, Object> questionWithAnswers = questionService.findByQuestionIdWithAnswers(questionId);
        log.debug("questionWithAnswers={}", questionWithAnswers);
        return jspView("qna/show").addObject("questionWithAnswers", questionWithAnswers);
    }

}
