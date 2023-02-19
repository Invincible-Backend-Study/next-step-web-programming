package next.controller.qna;

import core.mvcframework.ModelAndView;
import core.mvcframework.controller.AbstractController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import next.controller.qna.dto.AnswerCreateDto;
import next.model.User;
import next.service.AnswerService;
import next.utils.SessionUtil;

public class AnswerCreateController extends AbstractController {
    private final AnswerService answerService = new AnswerService();

    @Override
    public ModelAndView execute(final HttpServletRequest request, final HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (!SessionUtil.isLogined(session, "user")) {
            return jspView("redirect:/users/loginForm");
        }
        AnswerCreateDto answerCreateDto = createAnswerDto(request, session);
        answerService.insertNewAnswer(answerCreateDto.toModel());
        return jspView("redirect:/qna/showForm?questionId=" + answerCreateDto.getQuestionId());
    }

    private AnswerCreateDto createAnswerDto(final HttpServletRequest request, final HttpSession session) {
        User user = (User) session.getAttribute("user");
        return new AnswerCreateDto(
                user.getUserId(),
                request.getParameter("contents"),
                Long.parseLong(request.getParameter("questionId"))
        );
    }
}
