package next.controller.qna;

import core.mvcframework.ModelAndView;
import core.mvcframework.controller.AbstractController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.controller.qna.dto.QuestionUpdateFormDto;
import next.exception.CannotUpdateQuestionException;
import next.service.QuestionService;
import next.utils.SessionUtil;

public class QuestionUpdateFormController extends AbstractController {

    private final QuestionService questionService = new QuestionService();

    @Override
    public ModelAndView execute(final HttpServletRequest request, final HttpServletResponse response) {
        long questionId = Long.parseLong(request.getParameter("questionId"));
        String writer = request.getParameter("writer");
        try {
            return jspView("qna/update").addObject("question",
                    QuestionUpdateFormDto.from(questionService.findToUpdateQuestion(
                                    questionId,
                                    writer,
                                    SessionUtil.getLoginObject(request.getSession(), "user"))));
        } catch (CannotUpdateQuestionException exception) {
            return jspView("redirect:/");
        }
    }

}
