package next.service;

import core.annotation.Inject;
import core.annotation.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.exception.CannotDeleteQuestionException;
import next.exception.CannotUpdateQuestionException;
import next.model.Answer;
import next.model.Question;
import next.model.User;

@Service
public class QuestionService {

    private final QuestionDao questionDao;
    private final AnswerDao answerDao;

    @Inject
    public QuestionService(final QuestionDao questionDao, final AnswerDao answerDao) {
        this.questionDao = questionDao;
        this.answerDao = answerDao;
    }

    public List<Question> findAllOrderByCreatedDate() {
        return questionDao.findAllOrderByCreatedDate();
    }

    public void insertNewQuestion(final Question question) {
        questionDao.insertNewQuestion(question);
    }

    public Map<String, Object> findByQuestionIdWithAnswers(final long questionId) {
        HashMap<String, Object> questionWithAnswers = new HashMap<>();
        questionWithAnswers.put("question", questionDao.findById(questionId));
        questionWithAnswers.put("answers", answerDao.findAllByQuestionId(questionId));
        return questionWithAnswers;
    }

    public List<Question> findAll() {
        return questionDao.findAll();
    }

    public void updateQuestion(final Question question, final User user) {
        validateUpdateQuestion(question.getWriter(), user);
        questionDao.update(question);
    }

    public Question findToUpdateQuestion(final Long questionId, final String writer, final User user) {
        validateUpdateQuestion(writer, user);
        return questionDao.findById(questionId);
    }

    private void validateUpdateQuestion(final String writer, final User user) {
        if (!user.containUserId(writer)) {
            throw new CannotUpdateQuestionException("다른 사용자의 글은 수정할 수 없습니다.");
        }
    }

    /**
     * - 답변이 없는 경우 질문 삭제가 가능
     * <br>
     * - 질문자와 답변자가 모두 같은 경우 질문 삭제가 가능
     */
    public void deleteQuestion(final Long questionId, final User user) {
        Question question = questionDao.findById(questionId);
        if (question == null) {
            throw new CannotDeleteQuestionException("존재하지 않는 질문글은 삭제할 수 없습니다.");
        }
        List<Answer> answers = answerDao.findAllByQuestionId(questionId);
        if (question.canDelete(user, answers)) {
            answerDao.deleteAllByQuestionId(questionId);
            questionDao.deleteById(questionId);
        }
    }

}
