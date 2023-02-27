package next.api.qna.service;

import core.web.ModelAndView;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpSession;
import next.api.qna.dao.AnswerDao;
import next.api.qna.dao.QuestionDao;
import next.api.qna.model.Answer;
import next.api.qna.model.Question;
import next.api.user.model.User;
import next.common.model.Result;
import next.common.view.JsonView;

public class QuestionService {
    private static QuestionService questionService = new QuestionService();
    private QuestionService() {};
    public static QuestionService getInstance() {return questionService;};

    private final AnswerDao answerDao = AnswerDao.getInstance();
    private final QuestionDao questionDao = QuestionDao.getInstance();

    public void deleteQuestion(long questionId, User user) throws SQLException {
        Question question = questionDao.findByQuestionId(questionId);
        if (question == null) {
            throw new IllegalArgumentException("해당 번호의 질문이 없습니다.");
        }
        if (!user.getName().equals(question.getWriter())) {
            throw new IllegalArgumentException("자신이 작성한 질문만 삭제할 수 있습니다.");
        }

        List<Answer> answers = answerDao.findByQuestionId(questionId);
        if (!answers.isEmpty()) {
            boolean onlyMyAnswers = answers.stream().allMatch(answer -> answer.getWriter().equals(question.getWriter()));
            if (onlyMyAnswers) {
                questionDao.deleteByQuestionId(questionId);
            }
        }

        throw new IllegalArgumentException("다른사람에 답변이 있으면 제거할 수 없습니다.");
    }
}
