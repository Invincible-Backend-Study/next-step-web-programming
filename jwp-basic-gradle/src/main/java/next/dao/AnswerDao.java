package next.dao;

import core.jdbc.JdbcTemplate;
import java.util.List;
import next.model.Answer;

public class AnswerDao {
    private final JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public List<Answer> findAllByQuestionId(final long questionId) {
        return jdbcTemplate.query(
                "SELECT answerId, writer, contents, createdDate, questionId"
                        + " FROM ANSWERS "
                        + "WHERE questionId = ?",
                resultSet -> new Answer(
                        resultSet.getLong("answerId"),
                        resultSet.getString("writer"),
                        resultSet.getString("contents"),
                        resultSet.getDate("createdDate"),
                        resultSet.getLong("questionId")),
                questionId
        );
    }

    public int insert(final Answer answer) {
        return jdbcTemplate.update(
                "INSERT INTO ANSWERS(writer, contents, createdDate, questionId)"
                        + "VALUES(?, ?, ?, ?)",
                answer.getWriter(), answer.getContents(), answer.getCreatedDate(), answer.getQuestionId());
    }

    public int deleteById(final long answerId) {
        return jdbcTemplate.update(
                "DELETE FROM ANSWERS WHERE answerId = ?",
                answerId
        );
    }

    public Answer findById(final long answerId) {
        return jdbcTemplate.queryForObject(
                "SELECT answerId, writer, contents, createdDate, questionId FROM ANSWERS WHERE answerId = ?",
                resultSet -> new Answer(
                        resultSet.getLong("answerId"),
                        resultSet.getString("writer"),
                        resultSet.getString("contents"),
                        resultSet.getDate("createdDate"),
                        resultSet.getLong("questionId")
                ),
                answerId
        );
    }

}
