package next.service;

import java.util.List;
import java.util.Objects;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Form;
import next.model.Question;
import next.model.User;

public class QuestionService {

    private final QuestionDao questionDao;
    private final AnswerDao answerDao;

    public QuestionService(AnswerDao answerDao, QuestionDao questionDao) {
        this.answerDao = answerDao;
        this.questionDao = questionDao;
    }

    public Question findByQuestionId(int questionId) {
        return questionDao.findByQuestionId(questionId);
    }

    public List<Question> findAll() {
        return questionDao.findAll();
    }

    public void addQuestion(Question question) {
        questionDao.addQuestion(question);
    }

    public void updateQuestion(Form form, User user) throws Exception {
        validateUpdate(form, user);
        questionDao.update(form.getContents(), form.getTitle(), form.getId());
    }


    public void deleteQuestion(int questionId, User user) throws Exception {
        Question question = questionDao.findByQuestionId(questionId);
        List<Answer> answers = answerDao.findAllByQuestonId(questionId);
        question.candelete(user, answers);
        questionDao.deleteAnswer(question.getQuestionId());
        answers.forEach(answer -> answerDao.deleteAnswer(answer.getAnswerId()));
    }


    private void validateUpdate(Form form, User user) throws Exception {
        if (user == null) {
            throw new Exception("로그인 하지 않았습니다.");
        }
        Question question = questionDao.findByQuestionId(Integer.parseInt(form.getId()));
        if (!Objects.equals(user.getName(), question.getWriter())) {
            throw new Exception("작성자가 아닐시 수정할 수 없습니다.");
        }
    }
}
