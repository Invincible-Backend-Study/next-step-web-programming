package next.dao;

import core.jdbc.JdbcTemplete;
import core.jdbc.KeyHolder;
import next.model.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionDao {

    private final JdbcTemplete template = JdbcTemplete.getInstance();

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

    public Question findByQuestionId(int questionId) {
        String sql = "SELECT * FROM QUESTIONS WHERE QUESTIONID = ?";
        return template.excuteFindDynamicData(sql,rs -> {
            if(rs.next()){
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
        },questionId);
    }

    public List<Question> findAll() {
        String sql = "SELECT * FROM QUESTIONS";
        return template.excuteFindDynamicData(sql,rs -> {
            List<Question> questions = new ArrayList<>();
            while (rs.next()){
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

    public void decreaseAnswer(int questionId) {
        String sql = "UPDATE QUESTIONS SET countOfAnswer = countOfAnswer - 1 WHERE questionId = ?";
        template.excuteSqlUpdate(sql,questionId);
    }

    public void increaseAnswerCount(int questionId) {
        String sql = "UPDATE QUESTIONS SET countOfAnswer= countOfAnswer + 1 WHERE questionId = ?";
        template.excuteSqlUpdate(sql,questionId);
    }
}
