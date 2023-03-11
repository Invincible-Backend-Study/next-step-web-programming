package next.service;

import next.dao.JdbcAnswerDao;
import next.dao.JdbcQuestionDao;
import next.model.Answer;
import next.model.Question;

public class AnswerService {

    private final JdbcAnswerDao answerDao = new JdbcAnswerDao();
    private final JdbcQuestionDao questionDao = new JdbcQuestionDao();

    public Answer insertAnswer(final Answer answer) {
        Answer insertedAnswer = answerDao.insert(answer);
        if (insertedAnswer != null) {
            increaseCountOfAnswer(answer.getQuestionId());
            return insertedAnswer;
        }
        return null;
    }

    private void increaseCountOfAnswer(final Long questionId) {
        Question findQuestion = questionDao.findById(questionId);
        questionDao.updateCountOfAnswerById(questionId, findQuestion.getCountOfAnswer() + 1);
    }

    public int deleteAnswer(final long answerId) {
        Answer answer = answerDao.findById(answerId);
        Question question = questionDao.findById(answer.getQuestionId());

        int deleteResult = answerDao.deleteById(answerId);
        if (deleteResult > 0) {
            questionDao.updateCountOfAnswerById(answer.getQuestionId(), question.getCountOfAnswer() - 1);
        }
        return deleteResult;
    }

}
