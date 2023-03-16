package next.api.qna.dao;

import core.annotation.Repository;
import core.jdbc.JdbcTemplate;
import core.jdbc.ResultSetMapper;
import next.api.qna.model.Answer;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AnswerDao {
    static final JdbcTemplate jdbcTemplate = JdbcTemplate.getInstance();
//    private static AnswerDao answerDao = new AnswerDao();
//    private AnswerDao() {}
//    public static AnswerDao getInstance() {
//        return answerDao;
//    }

    public Answer insert(Answer answer) {
        String sql = "INSERT INTO ANSWERS(writer, contents, createdDate, questionId) VALUES (?, ?, ?, ?)";
        Long answerId = jdbcTemplate.insert(sql,
                answer.getWriter(),
                answer.getContents(),
                answer.getCreatedDate(),
                answer.getQuestionId()
        );
        return findByAnswerId(answerId);
    }

    public List<Answer> findAll() {
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

    public List<Answer> findByQuestionId(Long questionId) {
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

    public Answer findByAnswerId(Long answerId) {
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

    public int update(Answer answer) {
        String sql = "UPDATE ANSWERS SET contents=? WHERE answerId = ?";
        return jdbcTemplate.executeUpdate(sql,
                answer.getContents(),
                answer.getAnswerId()
        );
    }

    public int deleteByAnswerId(Long answerId) {
        String sql = "DELETE FROM ANSWERS WHERE answerId = ?";
        return jdbcTemplate.executeUpdate(sql, answerId);
    }

    public int deleteByQuestionId(Long questionId) {
        String sql = "DELETE FROM ANSWERS WHERE questionId = ?";
        return jdbcTemplate.executeUpdate(sql, questionId);
    }

    public int deleteAll() {
        String sql = "DELETE FROM ANSWERS";
        return jdbcTemplate.executeUpdate(sql);
    }
}
