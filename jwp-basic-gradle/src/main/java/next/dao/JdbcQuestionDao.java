package next.dao;

import core.jdbc.JdbcTemplete;
import core.jdbc.KeyHolder;
import next.model.Question;

import java.util.ArrayList;
import java.util.List;

public class JdbcQuestionDao implements QuestionDao {

    private final JdbcTemplete template = JdbcTemplete.getInstance();
    private static JdbcQuestionDao instance = null;

    private JdbcQuestionDao() {
    }

    public static synchronized JdbcQuestionDao getInstance() {
        if (instance == null) {
            instance = new JdbcQuestionDao();
        }
        return instance;
    }

    @Override
    public void addQuestion(Question question) {
        String sql = "INSERT INTO QUESTIONS ( writer, title, contents, createdDate, countOfAnswer) VALUES (?,?,?,?,?)";
        KeyHolder key = new KeyHolder();
        template.excuteSqlUpdate(
                key,
                sql,
                question.getWriter(),
                question.getTitle(),
                question.getContents(),
                question.getCreatedDate(),
                0
        );
    }

    @Override
    public Question findByQuestionId(int questionId) {
        String sql = "SELECT * FROM QUESTIONS WHERE QUESTIONID = ?";
        return template.excuteFindDynamicData(sql, rs -> {
            if (rs.next()) {
                return new Question(
                        rs.getInt("questionId"),
                        rs.getString("writer"),
                        rs.getString("title"),
                        rs.getString("contents"),
                        rs.getTimestamp("createdDate"),
                        rs.getInt("countOfAnswer")
                );

            }
            return null;
        }, questionId);
    }

    @Override
    public List<Question> findAll() {
        String sql = "SELECT * FROM QUESTIONS";
        return template.excuteFindDynamicData(sql, rs -> {
            List<Question> questions = new ArrayList<>();
            while (rs.next()) {
                questions.add(
                        new Question(
                                rs.getInt("questionId"),
                                rs.getString("writer"),
                                rs.getString("title"),
                                rs.getString("contents"),
                                rs.getTimestamp("createdDate"),
                                rs.getInt("countOfAnswer")
                        ));
            }
            return questions;
        });
    }

    @Override
    public void decreaseAnswer(int questionId) {
        String sql = "UPDATE QUESTIONS SET countOfAnswer = countOfAnswer - 1 WHERE questionId = ?";
        template.excuteSqlUpdate(sql, questionId);
    }

    @Override
    public void increaseAnswerCount(int questionId) {
        String sql = "UPDATE QUESTIONS SET countOfAnswer= countOfAnswer + 1 WHERE questionId = ?";
        template.excuteSqlUpdate(sql, questionId);
    }

    @Override
    public void deleteAnswer(int id) {
        String sql = "DELETE FROM QUESTIONS WHERE questionId = ?";
        template.excuteSqlUpdate(sql, id);
    }

    @Override
    public void update(String contents, String title, String id) {
        String sql = "UPDATE QUESTIONS SET CONTENTS = ?,TITLE =? WHERE questionId = ? ";
        template.excuteSqlUpdate(sql, contents, title, id);
    }
}
