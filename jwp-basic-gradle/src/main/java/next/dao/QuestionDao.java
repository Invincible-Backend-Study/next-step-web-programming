package next.dao;

import static next.dao.sql.QuestionSql.FIND_BY_ID;
import static next.dao.sql.QuestionSql.FindAll;
import static next.dao.sql.QuestionSql.SAVE;
import static next.dao.sql.QuestionSql.UPDATE_QUESTION_COUNT;

import core.jdbc.JdbcTemplate;
import java.util.List;
import next.model.Question;

public class QuestionDao {
    private final JdbcTemplate jdbcTemplate = new JdbcTemplate();
    public List<Question> findAll() {

        return jdbcTemplate.query(FindAll,
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
        return jdbcTemplate.queryForObject(FIND_BY_ID,
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
        jdbcTemplate.update(SAVE,
                preparedStatement -> {
                    preparedStatement.setString(1, question.getWriter());
                    preparedStatement.setString(2, question.getTitle());
                    preparedStatement.setString(3, question.getContents());
                }
        );
    }

    public void updateQuestionCount(Question updateQuestion) {
        jdbcTemplate.update(UPDATE_QUESTION_COUNT, preparedStatement -> {
            preparedStatement.setLong(1, updateQuestion.getCountOfComment());
            preparedStatement.setLong(2, updateQuestion.getQuestionId());
        });
    }
}
