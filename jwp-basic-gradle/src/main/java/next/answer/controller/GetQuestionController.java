package next.answer.controller;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import next.common.model.Result;


@Slf4j
public class GetQuestionController extends AbstractController {
    private final FindQuestionService findQuestionService = FindQuestionService.getInstance();

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        final var questionId = Long.parseLong(request.getParameter("questionId"));
        final var findQuestionResponse = findQuestionService.execute(questionId);

        return this.jsonView()
                .addObject("question", findQuestionResponse.getQuestion())
                .addObject("answers", findQuestionResponse.getAnswers())
                .addObject("result", Result.ok());
    }
}
