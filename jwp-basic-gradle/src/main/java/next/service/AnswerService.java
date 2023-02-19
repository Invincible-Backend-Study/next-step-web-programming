package next.service;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;

public class AnswerService {
    private final AnswerDao answerDao = new AnswerDao();
    private final QuestionDao questionDao = new QuestionDao();

    public void insertNewAnswer(final Answer answer) {
        answerDao.insert(answer);
        increaseCountOfAnswer(answer.getQuestionId());
    }

    private void increaseCountOfAnswer(final Long questionId) {
        Question findQuestion = questionDao.findByQuestionId(questionId);
        questionDao.updateCountOfAnswerById(questionId, findQuestion.getCountOfAnswer() + 1);
    }

    /**
     * AJAX 실습 insert
     */
    public Answer insertAnswer(final Answer answer) {
        if (answerDao.insert(answer) >= 1) {
            return answer;
        }
        return null;
    }

    public int deleteAnswer(final long answerId) {
        Answer answer = answerDao.findById(answerId);
        Question question = questionDao.findByQuestionId(answer.getQuestionId());

        int deleteResult = answerDao.deleteById(answerId);
        if (deleteResult > 0) {
            questionDao.updateCountOfAnswerById(answer.getQuestionId(), question.getCountOfAnswer() - 1);
        }
        return deleteResult;
    }
}
