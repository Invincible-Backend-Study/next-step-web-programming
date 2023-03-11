package next.service;

import java.util.List;
import java.util.Objects;
import next.dao.JdbcAnswerDao;
import next.dao.JdbcQuestionDao;
import next.model.Answer;
import next.model.Form;
import next.model.Question;
import next.model.User;

public class QuestionService {

    private final JdbcQuestionDao jdbcQuestionDao = JdbcQuestionDao.getInstance();
    private final JdbcAnswerDao jdbcAnswerDao = JdbcAnswerDao.getInstance()Q;



    public Question findByQuestionId(int questionId) {
        return jdbcQuestionDao.findByQuestionId(questionId);
    }

    public List<Question> findAll() {
        return jdbcQuestionDao.findAll();
    }

    public void addQuestion(Question question) {
        jdbcQuestionDao.addQuestion(question);
    }

    public void updateQuestion(Form form, User user) throws Exception {
        validateUpdate(form, user);
        jdbcQuestionDao.update(form.getContents(), form.getTitle(), form.getId());
    }

    private void validateUpdate(Form form, User user) throws Exception {
        if (user == null) {
            throw new Exception("로그인 하지 않았습니다.");
        }
        Question question = jdbcQuestionDao.findByQuestionId(Integer.parseInt(form.getId()));
        if (!Objects.equals(user.getName(), question.getWriter())) {
            throw new Exception("작성자가 아닐시 삭제할 수 없습니다.");
        }
    }

    public boolean deleteQuestion(int questionId, User user) throws Exception {
        Question question = jdbcQuestionDao.findByQuestionId(questionId);
        validateDelete(user, question);
        List<Answer> answers = jdbcAnswerDao.findAllByQuestonId(question.getQuestionId());
        boolean canDelete = answers.stream().allMatch(answer -> Objects.equals(answer.getWriter(), user.getName()));
        if (canDelete) {
            jdbcQuestionDao.deleteAnswer(question.getQuestionId());
            answers.forEach(answer -> jdbcAnswerDao.deleteAnswer(answer.getAnswerId()));
            return true;
        }
        return false;
    }

    private void validateDelete(User user, Question question) throws Exception {
        if (question == null) {
            throw new Exception("해당 게시물이 존재하지 않습니다.");
        }

        if (user == null) {
            throw new Exception("삭제하려면 로그인 하셔야합니다.");
        }

        if (!Objects.equals(user.getName(), question.getWriter())) {
            throw new Exception("삭제하려면 로그인 하셔야합니다.");
        }
    }
}
