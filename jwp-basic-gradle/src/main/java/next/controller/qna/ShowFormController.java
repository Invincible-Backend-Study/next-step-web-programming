package next.controller.qna;

import core.mvcframework.Controller;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.service.QuestionService;

public class ShowFormController implements Controller {
    private final QuestionService questionService = new QuestionService();

    @Override
    public String execute(final HttpServletRequest request, final HttpServletResponse response) {
        long questionId = Long.parseLong(request.getParameter("questionId"));
        Map<String, Object> questionWithAnswers = questionService.findByQuestionIdWithAnswers(questionId);
        request.setAttribute("questionWithAnswers", questionWithAnswers);
        return "qna/show";
    }
}
