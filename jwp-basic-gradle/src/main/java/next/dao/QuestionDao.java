package next.dao;

import core.jdbc.JdbcTemplate;
import core.jdbc.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import next.model.Question;

public class QuestionDao {
    private final JdbcTemplate jdbcTemplate = new JdbcTemplate();
    public List<Question> findAll() {
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
        String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS "
                + "WHERE questionId = ?";


        return jdbcTemplate.queryForObject(sql,
                preparedStatement -> preparedStatement.setLong(1,questionId),
                resultSet -> new Question(
                        resultSet.getLong("questionId"),
                        resultSet.getString("writer"),
                        resultSet.getString("title"),
                        resultSet.getString("contents"),
                        resultSet.getTimestamp("createdDate"),
                        resultSet.getInt("countOfAnswer")
                )
        );
    }

    public void save(Question question) {
        // 분리 예정
        String sql = "INSERT INTO QUESTIONS (writer, title, contents, createdDate) VALUES (?, ?, ?, now())";

        jdbcTemplate.update(sql,
                preparedStatement -> {
                    preparedStatement.setString(1, question.getWriter());
                    preparedStatement.setString(2, question.getTitle());
                    preparedStatement.setString(3, question.getContents());
                }
        );
    }
}
