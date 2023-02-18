package next.dao;

import core.jdbc.JdbcTemplate;
import core.jdbc.ResultSetMapper;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import next.model.Answer;
import next.model.Question;

public class AnswerDao {
    static final JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public static Answer insert(Answer answer) throws SQLException {
        String sql = "INSERT INTO ANSWERS(writer, contents, createdDate, questionId) VALUES (?, ?, ?, ?)";
        Long answerId = jdbcTemplate.insert(sql,
                answer.getWriter(),
                answer.getContents(),
                answer.getCreatedDate(),
                answer.getQuestionId()
        );
        return findByAnswerId(answerId);
    }

    public static List<Answer> findAll() throws SQLException {
        ResultSetMapper<List<Answer>> resultSetMapper = rs -> {
            List<Answer> answers = new ArrayList<>();
            while (rs.next()) {
                answers.add(new Answer(
                        rs.getLong("answerId"),
                        rs.getString("writer"),
                        rs.getString("contents"),
                        rs.getTimestamp("createdDate"),
                        rs.getLong("questionId")
                ));
            }
            return answers;
        };

        String sql = "SELECT answerId, writer, contents, createdDate, questionId FROM ANSWERS";
        return jdbcTemplate.select(sql, resultSetMapper);
    }

    public static List<Answer> findByQuestionId(Long questionId) throws SQLException {
        ResultSetMapper<List<Answer>> resultSetMapper = rs -> {
            List<Answer> answers = new ArrayList<>();
            while (rs.next()) {
                answers.add(new Answer(
                        rs.getLong("answerId"),
                        rs.getString("writer"),
                        rs.getString("contents"),
                        rs.getTimestamp("createdDate"),
                        rs.getLong("questionId")
                ));
            }
            return answers;
        };

        String sql = "SELECT answerId, writer, contents, createdDate, questionId FROM ANSWERS WHERE questionId = ?";
        return jdbcTemplate.select(sql, resultSetMapper, questionId);
    }

    public static Answer findByAnswerId(Long answerId) throws SQLException {
        ResultSetMapper<Answer> resultSetMapper = rs -> {
            if (rs.next()) {
                return new Answer(
                        rs.getLong("answerId"),
                        rs.getString("writer"),
                        rs.getString("contents"),
                        rs.getTimestamp("createdDate"),
                        rs.getLong("questionId")
                );
            }
            return null;
        };

        String sql = "SELECT answerId, writer, contents, createdDate, questionId FROM ANSWERS WHERE answerId = ?";
        return jdbcTemplate.select(sql, resultSetMapper, answerId);
    }

    public static int update(Answer answer) throws SQLException {
        String sql = "UPDATE ANSWERS SET contents=? WHERE answerId = ?";
        return jdbcTemplate.executeUpdate(sql,
                answer.getContents(),
                answer.getAnswerId()
        );
    }

    public static int deleteAll() throws SQLException {
        String sql = "DELETE FROM ANSWERS";
        return jdbcTemplate.executeUpdate(sql);
    }
}
