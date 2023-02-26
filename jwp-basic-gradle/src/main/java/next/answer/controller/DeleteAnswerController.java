package next.answer.controller;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.answer.dao.AnswerDao;
import next.common.model.Result;
import next.qna.dao.QuestionDao;

public class DeleteAnswerController extends AbstractController {
    private final AnswerDao answerDao = new AnswerDao();
    private final QuestionDao questionDao = new QuestionDao();

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        final var answerId = Long.parseLong(request.getParameter("answerId"));
        final var answer = answerDao.findById(answerId);

        if (answer == null) {
            return this.jsonView()
                    .addObject("result", Result.fail("질문이 존재하지 않습니다."))
                    .addObject("answer", null);
        }
        final var question = questionDao.findById(answer.getQuestionId());
        if (question == null) {
            return this.jsonView()
                    .addObject("result", Result.fail("없음"))
                    .addObject("answer", null);
        }

        final var updateQuestion = question.minusAnswerCountByOne();
        this.answerDao.deleteById(question.getQuestionId(), answerId);
        this.questionDao.updateQuestionCount(updateQuestion);

        return this.jsonView()
                .addObject("result", Result.ok())
                .addObject("countOfComments", updateQuestion.getCountOfComment());
    }
}
