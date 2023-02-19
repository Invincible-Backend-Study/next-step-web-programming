package next.dao;

import core.jdbc.JdbcTemplate;
import core.jdbc.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import next.model.Question;

public class QuestionDao {
    public List<Question> findAll() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT questionId, writer, title, createdDate, countOfAnswer FROM QUESTIONS "
                + "order by questionId desc";

        return jdbcTemplate.query(sql,
                preparedStatement -> {},
                resultSet -> new Question(
                        resultSet.getLong("questionId"),
                        resultSet.getString("writer"),
                        resultSet.getString("title"),
                        null,
                        resultSet.getTimestamp("createdDate"),
                        resultSet.getInt("countOfAnswer")));
    }

    public Question findById(long questionId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS "
                + "WHERE questionId = ?";


        return jdbcTemplate.queryForObject(sql, preparedStatement -> preparedStatement.setLong(1,questionId),
                resultSet ->
                        new Question(resultSet.getLong("questionId"), resultSet.getString("writer"), resultSet.getString("title"),
                                resultSet.getString("contents"), resultSet.getTimestamp("createdDate"), resultSet.getInt("countOfAnswer"))
        );
    }
}
