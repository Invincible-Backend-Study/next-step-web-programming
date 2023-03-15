package next.qna.ui;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.common.utils.UserUtils;
import next.qna.dao.QuestionDao;

public class UpdateQnaFormController extends AbstractController {
    private final QuestionDao questionDao = QuestionDao.getInstance();

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) {

        // 로그인을 하지 않은 사용자에 대한 접근 차단
        if (!UserUtils.isLoggedIn(request.getSession())) {
            return this.jspView("redirect: /");
        }

        final var questionId = this.convertHttpToDto(request);
        final var optionalQuestion = questionDao.findOptionalById(questionId);

        // 존재하지 않는 게시글의 경우 리턴
        if (optionalQuestion.isEmpty()) {
            return this.jspView("redirect: /" + questionId);
        }

        final var question = optionalQuestion.get();
        final var logedInUser = UserUtils.getUserBy(request);

        // 작성자와 로그인한 사용자가 다른 경우
        if (!Objects.equals(question.getWriter(), logedInUser.getName())) {
            return this.jspView("redirect: /qna/show?questionId=" + questionId);
        }

        return this.jspView("/WEB-INF/qna/update.jsp")
                .addObject("question", question);
    }

    public long convertHttpToDto(HttpServletRequest request) {
        try {
            return Integer.parseInt(request.getParameter("questionId"));
        } catch (NumberFormatException e) {
            return 0L;
        }
    }
}
