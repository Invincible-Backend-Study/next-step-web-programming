package next.service;

import core.annotation.Inject;
import next.dao.JdbcAnswerDao;
import next.dao.JdbcQuestionDao;
import next.model.Answer;
import next.model.Question;

public class AnswerService {

    private final JdbcAnswerDao answerDao;
    private final JdbcQuestionDao questionDao;

    @Inject
    public AnswerService(final JdbcAnswerDao answerDao, final JdbcQuestionDao questionDao) {
        this.answerDao = answerDao;
        this.questionDao = questionDao;
    }

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
