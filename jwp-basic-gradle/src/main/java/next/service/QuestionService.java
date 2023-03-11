package next.service;

import java.util.List;
import java.util.Objects;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.model.User;

public class QuestionService {

    private final QuestionDao questionDao = QuestionDao.getInstance();
    private final AnswerDao answerDao = AnswerDao.getInstance();

    public Question findByQuestionId(int questionId) {
        return questionDao.findByQuestionId(questionId);
    }

    public boolean deleteQuestion(User user, Question question) {
        List<Answer> answers = answerDao.findAllByQuestonId(question.getQuestionId());
        boolean canDelete = answers.stream().allMatch(answer -> Objects.equals(answer.getWriter(), user.getName()));
        if (canDelete) {
            questionDao.deleteAnswer(question.getQuestionId());
            answers.forEach(answer -> answerDao.deleteAnswer(answer.getAnswerId()));
            return true;
        } else {
            return false;
        }
    }

    public List<Question> findAll() {
        return questionDao.findAll();
    }

    public void addQuestion(Question question) {
        questionDao.addQuestion(question);
    }

    public void updateQuestion(String contents, String title, String id) {
        questionDao.update(contents, title, id);
    }
}
