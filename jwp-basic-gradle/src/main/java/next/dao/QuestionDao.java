package next.dao;

import core.jdbc.JdbcTemplete;
import next.model.Question;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestionDao {

    private final JdbcTemplete template = new JdbcTemplete();

    public void addQuestion(Question question) throws SQLException {
        String sql = "INSERT INTO QUESTIONS ( writer, title, contents, createdDate, countOfAnswer) VALUES (?,?,?,?,?)";
        template.excuteSqlUpdate(
                sql,
                question.getWriter(),
                question.getTitle(),
                question.getContents(),
                question.getCreatedDate(),
                0
        );
    }

    public Question findByQuestionId(int questionId) throws SQLException {
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

    public List<Question> findAll() throws SQLException {
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
}
