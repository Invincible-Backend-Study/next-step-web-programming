package next.dao;

import core.jdbc.JdbcTemplate;
import core.jdbc.KeyHolder;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;
import next.model.Answer;

public class AnswerDao {
    private final JdbcTemplate jdbcTemplate = new JdbcTemplate();
    public Answer insert(Answer answer) {
        String sql = "INSERT INTO ANSWERS (writer, contents, createdDate, questionId) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new KeyHolder();
        jdbcTemplate.update((connection) ->{
            // PreparedStatement.Return_GeneratedKey를 붙혀주지 않으면 오류가 발생함
            PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, answer.getWriter());
            pstmt.setString(2, answer.getContents());
            pstmt.setTimestamp(3, new Timestamp(answer.getTimeFromCreateDate()));
            pstmt.setLong(4, answer.getQuestionId());
            return pstmt;
        }, keyHolder);
        return findById(keyHolder.getId());
    }


    public Answer findById(long answerId) {
        String sql = "SELECT answerId, writer, contents, createdDate, questionId FROM ANSWERS WHERE answerId = ?";

        return jdbcTemplate.queryForObject(sql, ( preparedStatement)-> preparedStatement.setLong(1,answerId),
                (rs) -> new Answer(rs.getLong("answerId"), rs.getString("writer"), rs.getString("contents"),
                        rs.getTimestamp("createdDate"), rs.getLong("questionId")));
    }

    public List<Answer> findAllByQuestionId(long questionId) {
        String sql = "SELECT answerId, writer, contents, createdDate FROM ANSWERS WHERE questionId = ? order by answerId desc";

        return jdbcTemplate.query(sql, (preparedStatement -> preparedStatement.setLong(1, questionId)),
                (rs)-> new Answer(rs.getLong("answerId"),
                        rs.getString("writer"),
                        rs.getString("contents"),
                        rs.getTimestamp("createdDate"), questionId)
        );
    }

    public void deleteById(long questionId,long answerId) {
        String sql = "DELETE FROM ANSWERS where questionId = ? and answerId = ?";
        jdbcTemplate.update(sql, (preparedStatement -> {
            preparedStatement.setLong(1, questionId);
            preparedStatement.setLong(2, answerId);
        }));
    }
}
