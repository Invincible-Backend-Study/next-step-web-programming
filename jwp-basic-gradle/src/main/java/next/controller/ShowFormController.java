package next.controller;

import core.mvcframework.Controller;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;

public class ShowFormController implements Controller {
    private final QuestionDao questionDao = new QuestionDao();
    private final AnswerDao answerDao = new AnswerDao();

    @Override
    public String execute(final HttpServletRequest request, final HttpServletResponse response) {
        long questionId = Long.parseLong(request.getParameter("questionId"));
        Question question = questionDao.findByQuestionId(questionId);
        List<Answer> answers = answerDao.findAllByQuestionId(questionId);
        request.setAttribute("question", question);
        request.setAttribute("answers", answers);
        return "qna/show";
    }
}
