package next.qna.ui;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.common.utils.UserUtils;
import next.qna.dao.QuestionDao;
import next.qna.payload.request.UpdateQuestionRequest;

public class UpdateQnaActionController extends AbstractController {
    private final QuestionDao questionDao = QuestionDao.getInstance();

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!UserUtils.isLoggedIn(request.getSession())) {
            return this.jspView("redirect: /");
        }
        final var user = UserUtils.getUserBy(request);
        final var updateQuestionRequest = this.convertHttpRequestToPayloadRequest(request);
        final var optionalQuestion = questionDao.findOptionalById(updateQuestionRequest.getQuestionId());

        if (optionalQuestion.isEmpty()) {
            return this.jspView("redirect: /");
        }
        final var question = optionalQuestion.get();

        if (!Objects.equals(question.getWriter(), user.getName())) {
            return this.jspView("redirect: /");
        }

        questionDao.update(updateQuestionRequest.toEntity());

        return this.jspView("redirect: /");
    }

    private UpdateQuestionRequest convertHttpRequestToPayloadRequest(HttpServletRequest request) {
        return UpdateQuestionRequest.of(
                Long.valueOf(request.getParameter("questionId")),
                request.getParameter("writer"),
                request.getParameter("title"),
                request.getParameter("contents")
        );
    }
}
