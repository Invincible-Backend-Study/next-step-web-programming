package next.answer.controller;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import next.answer.dao.AnswerDao;
import next.answer.model.Answer;
import next.common.model.Result;
import next.common.utils.UserUtils;
import next.qna.dao.QuestionDao;


@Slf4j
public class AddAnswerController extends AbstractController {
    private final AnswerDao answerDao = AnswerDao.getInstance();
    private final QuestionDao questionDao = QuestionDao.getInstance();

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        if (!UserUtils.isLoggedIn(request.getSession())) {
            return this.jsonView()
                    .addObject("result", Result.fail("로그인한 사용자만 작성할 수 있습니다."));
        }

        final var user = UserUtils.getUserBy(request);

        final var questionId = Long.parseLong(request.getParameter("questionId"));
        final var question = questionDao.findById(questionId);

        if (question == null) {
            return this.jsonView().addObject("answer", null);
        }

        Answer answer = new Answer(user.getName(), request.getParameter("contents"), questionId);
        Answer savedAnswer = answerDao.insert(answer);
        final var updateQuestion = question.plusAnswerCountByOne();
        questionDao.updateQuestionCount(updateQuestion);

        return this.jsonView()
                .addObject("result", Result.ok())
                .addObject("answer", savedAnswer)
                .addObject("countOfComments", updateQuestion.getCountOfComment());
    }
}
