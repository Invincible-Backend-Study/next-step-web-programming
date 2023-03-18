package next.dao;

import core.annotation.Repository;
import core.jdbc.JdbcTemplate;
import core.jdbc.KeyHolder;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;
import next.model.Answer;

@Repository
public class JdbcAnswerDao implements AnswerDao {

    private static final JdbcTemplate jdbcTemplate = JdbcTemplate.getInstance();

    public List<Answer> findAllByQuestionId(final long questionId) {
        return jdbcTemplate.query(
                "SELECT answerId, writer, contents, createdDate, questionId"
                        + " FROM ANSWERS "
                        + "WHERE questionId = ?",
                resultSet -> new Answer(
                        resultSet.getLong("answerId"),
                        resultSet.getString("writer"),
                        resultSet.getString("contents"),
                        resultSet.getTimestamp("createdDate"),
                        resultSet.getLong("questionId")),
                questionId
        );
    }

    public Answer insert(final Answer answer) {
        KeyHolder keyHolder = new KeyHolder();
        String sql = "INSERT INTO ANSWERS(writer, contents, createdDate, questionId) VALUES(?, ?, ?, ?)";
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, answer.getWriter());
                    preparedStatement.setString(2, answer.getContents());
                    preparedStatement.setTimestamp(3, new Timestamp(answer.getTimeFromCreatedDate()));
                    preparedStatement.setLong(4, answer.getQuestionId());
                    return preparedStatement;
                }, keyHolder);
        return findById(keyHolder.getId());
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
                        resultSet.getTimestamp("createdDate"),
                        resultSet.getLong("questionId")
                ),
                answerId
        );
    }

    public int deleteAllByQuestionId(final Long questionId) {
        return jdbcTemplate.update(
                "DELETE FROM ANSWERS WHERE questionId = ?",
                questionId
        );
    }

}
