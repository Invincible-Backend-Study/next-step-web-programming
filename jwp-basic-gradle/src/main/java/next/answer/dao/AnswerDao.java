package next.answer.dao;

import static next.answer.dao.sql.AnswerSql.DELETE;
import static next.answer.dao.sql.AnswerSql.FIND_ALL_BY_QUESTION_ID;
import static next.answer.dao.sql.AnswerSql.FIND_BY_ID;
import static next.answer.dao.sql.AnswerSql.INSERT;

import core.jdbc.JdbcTemplate;
import core.jdbc.KeyHolder;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;
import next.answer.dao.sql.AnswerSql;
import next.answer.model.Answer;

public class AnswerDao {
    private final JdbcTemplate jdbcTemplate = JdbcTemplate.getInstance();

    public static AnswerDao getInstance() {
        return AnswerDaoHolder.ANSWER_DAO;
    }

    public Answer insert(Answer answer) {
        final var keyHolder = new KeyHolder();

        jdbcTemplate.update((connection) -> {
            // PreparedStatement.Return_GeneratedKey를 붙혀주지 않으면 오류가 발생함
            PreparedStatement pstmt = connection.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, answer.getWriter());
            pstmt.setString(2, answer.getContents());
            pstmt.setTimestamp(3, new Timestamp(answer.getTimeFromCreateDate()));
            pstmt.setLong(4, answer.getQuestionId());
            return pstmt;
        }, keyHolder);

        return this.findById(keyHolder.getId());
    }

    public Answer findById(long answerId) {
        return jdbcTemplate.queryForObject(FIND_BY_ID,
                preparedStatement -> preparedStatement.setLong(1, answerId),
                resultSet -> new Answer(
                        resultSet.getLong("answerId"),
                        resultSet.getString("writer"),
                        resultSet.getString("contents"),
                        resultSet.getTimestamp("createdDate"),
                        resultSet.getLong("questionId"))
        );
    }

    public List<Answer> findAllByQuestionId(long questionId) {
        return jdbcTemplate.query(FIND_ALL_BY_QUESTION_ID,
                preparedStatement -> preparedStatement.setLong(1, questionId),
                resultSet -> new Answer(
                        resultSet.getLong("answerId"),
                        resultSet.getString("writer"),
                        resultSet.getString("contents"),
                        resultSet.getTimestamp("createdDate"),
                        questionId
                )
        );
    }

    public int deleteById(long questionId, long answerId) {
        return jdbcTemplate.update(DELETE, (preparedStatement -> {
            preparedStatement.setLong(1, questionId);
            preparedStatement.setLong(2, answerId);
        }));
    }

    public void deleteByIds(List<Long> ids) {
        jdbcTemplate.update(AnswerSql.DELETE_ID_List(ids), preparedStatement -> {
        });
    }

    private static class AnswerDaoHolder {
        private static final AnswerDao ANSWER_DAO = new AnswerDao();
    }
}
