package next.api.qna.dao;

import core.annotation.Repository;
import core.jdbc.JdbcTemplate;
import core.jdbc.ResultSetMapper;
import next.api.qna.model.Question;

import java.util.ArrayList;
import java.util.List;

@Repository
public class QuestionDao {
    static final JdbcTemplate jdbcTemplate = JdbcTemplate.getInstance();
//    private static QuestionDao questionDao = new QuestionDao();
//    private QuestionDao() {}
//    public static QuestionDao getInstance() {
//        return questionDao;
//    }

    public Question insert(Question question) {
        String sql = "INSERT INTO QUESTIONS(writer, title, contents, createdDate, countOfAnswer) VALUES (?, ?, ?, ?, ?)";
        Long questionId = jdbcTemplate.insert(sql,
                question.getWriter(),
                question.getTitle(),
                question.getContents(),
                question.getCreatedDate(),
                question.getCountOfAnswer());
        return findByQuestionId(questionId);
    }

    public List<Question> findAll() {
        ResultSetMapper<List<Question>> resultSetMapper = rs -> {
            List<Question> questions = new ArrayList<>();
            while (rs.next()) {
                questions.add(new Question(
                        rs.getLong("questionId"),
                        rs.getString("writer"),
                        rs.getString("title"),
                        rs.getString("contents"),
                        rs.getInt("countOfAnswer"),
                        rs.getTimestamp("createdDate")
                ));
            }
            return questions;
        };

        String sql = "SELECT questionId, writer, title, contents, countOfAnswer, createdDate FROM QUESTIONS";
        return jdbcTemplate.select(sql, resultSetMapper);
    }

    public Question findByQuestionId(Long questionId) {
        ResultSetMapper<Question> resultSetMapper = rs -> {
            if (rs.next()) {
                return new Question(
                        rs.getLong("questionId"),
                        rs.getString("writer"),
                        rs.getString("title"),
                        rs.getString("contents"),
                        rs.getInt("countOfAnswer"),
                        rs.getTimestamp("createdDate")
                );
            }
            return null;
        };

        String sql = "SELECT questionId, writer, title, contents, countOfAnswer, createdDate FROM QUESTIONS WHERE questionId = ?";
        return jdbcTemplate.select(sql, resultSetMapper, questionId);
    }

    public int update(Question question) {
        String sql = "UPDATE QUESTIONS SET title=?, contents=?, countOfAnswer=? WHERE questionId = ?";
        return jdbcTemplate.executeUpdate(sql,
                question.getTitle(),
                question.getContents(),
                question.getCountOfAnswer(),
                question.getQuestionId());
    }

    public int deleteByQuestionId(Long questionId) {
        String sql = "DELETE FROM QUESTIONS WHERE questionId = ?";
        return jdbcTemplate.executeUpdate(sql, questionId);
    }

    public int deleteAll() {
        String sql = "DELETE FROM QUESTIONS";
        return jdbcTemplate.executeUpdate(sql);
    }
}
