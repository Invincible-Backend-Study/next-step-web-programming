package next.controller.qna.legacy;

import core.mvcframework.ModelAndView;
import core.mvcframework.controller.AbstractController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.controller.qna.dto.QuestionCreateDto;
import next.service.QuestionService;

public class QuestionCreateController extends AbstractController {

    private final QuestionService questionService;

    public QuestionCreateController(final QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public ModelAndView execute(final HttpServletRequest request, final HttpServletResponse response) {
        QuestionCreateDto questionCreateDto = QuestionCreateDto.createDto(
                request.getParameter("writer"),
                request.getParameter("title"),
                request.getParameter("contents")
        );
        questionService.insertNewQuestion(questionCreateDto.toModel());
        return jspView("redirect:/");
    }
}
