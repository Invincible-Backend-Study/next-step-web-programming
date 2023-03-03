package next.controller.qna.legacy;

import core.annotation.Controller;
import core.mvcframework.ModelAndView;
import core.mvcframework.controller.AbstractController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.controller.qna.dto.QuestionCreateDto;
import next.service.QuestionService;

@Controller
public class QuestionCreateController extends AbstractController {

    private final QuestionService questionService = new QuestionService();

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
