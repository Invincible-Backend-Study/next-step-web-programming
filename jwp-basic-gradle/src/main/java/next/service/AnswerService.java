package next.service;

import java.util.List;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;

public class AnswerService {

    private final AnswerDao answerDao = new AnswerDao();
    private final QuestionDao questionDao  = new QuestionDao();
    public Answer addAnswer(Answer answer){
        Answer returnedAnswer= answerDao.addAnswer(answer);
        if(returnedAnswer == null){
            return null;
        }
        questionDao.increaseAnswerCount(answer.getQuestionId());
        return returnedAnswer;
    }

    public void deleteAnswer(int answerId){
        answerDao.deleteAnswer(answerId);
        questionDao.decreaseAnswer(answerId);
    }

    public List<Answer> findAllbyQuestionId(int questionId) {
        return answerDao.findAllByQuestonId(questionId);
    }
}
