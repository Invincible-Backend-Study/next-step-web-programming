package next.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Question;

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
        questionWithAnswers.put("question", questionDao.findByQuestionId(questionId));
        questionWithAnswers.put("answers", answerDao.findAllByQuestionId(questionId));
        return questionWithAnswers;
    }

    public List<Question> findAll() {
        return questionDao.findAll();
    }

    public void updateQuestion(final Question question) {
        questionDao.updateQuestion(question);
    }

    public Question findById(final Long questionId) {
        return questionDao.findByQuestionId(questionId);
    }
}
