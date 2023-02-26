package next.controller.answer;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowController extends AbstractController {
    private final FindQuestionService questionService = FindQuestionService.getInstance();

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        final var questionId = Long.parseLong(request.getParameter("questionId"));

        final var findQuestionResponse = questionService.execute(questionId);

        request.setAttribute("question", findQuestionResponse.getQuestion());
        request.setAttribute("answers", findQuestionResponse.getAnswers());
        
        return this.jspView("/WEB-INF/qna/show.jsp");
    }
}
