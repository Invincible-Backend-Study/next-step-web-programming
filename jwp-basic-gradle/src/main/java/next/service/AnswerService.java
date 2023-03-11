package next.service;

import java.util.List;
import next.dao.JdbcAnswerDao;
import next.dao.JdbcQuestionDao;
import next.model.Answer;

public class AnswerService {

    private final JdbcAnswerDao jdbcAnswerDao = JdbcAnswerDao.getInstance();
    private final JdbcQuestionDao jdbcQuestionDao = JdbcQuestionDao.getInstance();

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
