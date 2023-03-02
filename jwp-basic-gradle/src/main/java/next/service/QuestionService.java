package next.service;

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

public class QuestionService {

    private final QuestionDao questionDao = new QuestionDao();
    private final AnswerDao answerDao = new AnswerDao();

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
        List<Answer> answers = answerDao.findAllByQuestionId(questionId);
        Question question = questionDao.findById(questionId);
        validateDelete(user, answers, question);
        answerDao.deleteAllByQuestionId(questionId);
        questionDao.deleteById(questionId);
    }

    private void validateDelete(final User user, final List<Answer> answers, final Question question) {
        if (!user.getUserId().equals(question.getWriter())) {
            throw new CannotDeleteQuestionException("다른 사용자의 글은 삭제할 수 없습니다.");
        }
        if (answers.size() > 0 && !isAllSameWriter(question.getWriter(), answers)) {
            throw new CannotDeleteQuestionException("질문에 다른 사용자의 답변이 달려있으므로 삭제할 수 없습니다.");
        }
    }

    private boolean isAllSameWriter(final String questionWriter, final List<Answer> answers) {
        return answers.stream()
                .allMatch(answer -> answer.isWriter(questionWriter));
    }

}
