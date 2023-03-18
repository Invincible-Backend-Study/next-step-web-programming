package next.controller.qna.legacy;

import core.mvcframework.ModelAndView;
import core.mvcframework.controller.AbstractController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.controller.qna.dto.QuestionUpdateFormDto;
import next.exception.CannotUpdateQuestionException;
import next.service.QuestionService;
import next.utils.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuestionUpdateController extends AbstractController {

    private static final Logger log = LoggerFactory.getLogger(QuestionUpdateController.class);

    private final QuestionService questionService;

    public QuestionUpdateController(final QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public ModelAndView execute(final HttpServletRequest request, final HttpServletResponse response) {
        try {
            QuestionUpdateFormDto requestDto = QuestionUpdateFormDto.createRequestDto(
                    request.getParameter("questionId"),
                    request.getParameter("writer"),
                    request.getParameter("title"),
                    request.getParameter("contents")
            );
            questionService.updateQuestion(
                    requestDto.toModel(),
                    SessionUtil.getLoginObject(request.getSession(), "user")
            );
            return jspView("redirect:/qna/show?questionId=" + requestDto.getQuestionId());
        } catch (CannotUpdateQuestionException exception) {
            log.error(exception.getMessage());
            return jspView("redirect:/");
        }

    }

}
