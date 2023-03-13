package next.service;

import java.util.List;
import next.dao.AnswerDao;
import next.dao.JdbcAnswerDao;
import next.dao.JdbcQuestionDao;
import next.dao.QuestionDao;
import next.model.Answer;

public class AnswerService {

    private final AnswerDao jdbcAnswerDao;
    private final QuestionDao jdbcQuestionDao;

    public AnswerService(AnswerDao answerDao, QuestionDao questionDao) {
        this.jdbcAnswerDao = answerDao;
        this.jdbcQuestionDao = questionDao;
    }

    public Answer addAnswer(Answer answer) {
        Answer returnedAnswer = jdbcAnswerDao.addAnswer(answer);
        if (returnedAnswer == null) {
            return null;
        }
        jdbcQuestionDao.increaseAnswerCount(answer.getQuestionId());
        return returnedAnswer;
    }

    public void deleteAnswer(int answerId) {
        jdbcAnswerDao.deleteAnswer(answerId);
        jdbcQuestionDao.decreaseAnswer(answerId);
    }

    public List<Answer> findAllbyQuestionId(int questionId) {
        return jdbcAnswerDao.findAllByQuestonId(questionId);
    }
}
