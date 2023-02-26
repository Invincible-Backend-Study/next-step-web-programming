package next.controller.qna;

import core.mvcframework.ModelAndView;
import core.mvcframework.controller.AbstractController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.controller.qna.dto.QuestionUpdateFormDto;
import next.service.QuestionService;

public class QuestionUpdateController extends AbstractController {

    private final QuestionService questionService = new QuestionService();

    @Override
    public ModelAndView execute(final HttpServletRequest request, final HttpServletResponse response) {
        QuestionUpdateFormDto requestDto = QuestionUpdateFormDto.createRequestDto(
                request.getParameter("questionId"),
                request.getParameter("writer"),
                request.getParameter("title"),
                request.getParameter("contents")
        );
        questionService.updateQuestion(requestDto.toModel());

        return jspView("redirect:/qna/show?questionId=" + requestDto.getQuestionId());
    }

}
