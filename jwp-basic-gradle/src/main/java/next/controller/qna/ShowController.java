package next.controller.qna;

import core.mvcframework.Controller;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShowController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(ShowController.class);
    private final QuestionService questionService = new QuestionService();

    @Override
    public String execute(final HttpServletRequest request, final HttpServletResponse response) {
        long questionId = Long.parseLong(request.getParameter("questionId"));
        Map<String, Object> questionWithAnswers = questionService.findByQuestionIdWithAnswers(questionId);
        log.debug("questionWithAnswers={}", questionWithAnswers);
        request.setAttribute("questionWithAnswers", questionWithAnswers);
        return "qna/show";
    }
}
