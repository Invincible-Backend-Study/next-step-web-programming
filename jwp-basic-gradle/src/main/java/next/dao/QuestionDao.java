package next.dao;

import core.jdbc.JdbcTemplate;
import java.util.List;
import next.model.Question;

public class QuestionDao {
    private final JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public List<Question> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM QUESTIONS",
                resultSet ->
                        new Question(
                                resultSet.getLong("questionId"),
                                resultSet.getString("writer"),
                                resultSet.getString("title"),
                                resultSet.getString("contents"),
                                resultSet.getDate("createdDate"),
                                resultSet.getLong("countOfAnswer"))
        );
    }
}
