package next.service;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;

public class AnswerService {

    private final AnswerDao answerDao = new AnswerDao();
    private final QuestionDao questionDao = new QuestionDao();

    public Answer insertAnswer(final Answer answer) {
        if (answerDao.insert(answer) >= 1) {
            increaseCountOfAnswer(answer.getQuestionId());
            return answer;
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
