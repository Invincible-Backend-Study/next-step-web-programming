package next.controller.qna;

import core.mvcframework.Controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import next.controller.qna.dto.AnswerCreateDto;
import next.model.User;
import next.service.AnswerService;
import next.utils.SessionUtil;

public class AnswerCreateController implements Controller {
    private final AnswerService answerService = new AnswerService();
    @Override
    public String execute(final HttpServletRequest request, final HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (!SessionUtil.isLogin(session, "user")) {
            return "redirect:/users/loginForm";
        }
        AnswerCreateDto answerCreateDto = createAnswerDto(request, session);
        answerService.insertNewAnswer(answerCreateDto.toModel());
        return "redirect:/qna/showForm?questionId=" + answerCreateDto.getQuestionId();
    }

    private static AnswerCreateDto createAnswerDto(final HttpServletRequest request, final HttpSession session) {
        User user = (User) session.getAttribute("user");
        return new AnswerCreateDto(
                user.getUserId(),
                request.getParameter("contents"),
                Long.parseLong(request.getParameter("questionId"))
        );
    }
}
