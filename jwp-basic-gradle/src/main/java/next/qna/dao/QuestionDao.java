package next.qna.dao;

import static next.qna.sql.QuestionSql.FIND_BY_ID;
import static next.qna.sql.QuestionSql.FindAll;
import static next.qna.sql.QuestionSql.SAVE;
import static next.qna.sql.QuestionSql.UPDATE_QUESTION_COUNT;

import core.jdbc.JdbcTemplate;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import next.qna.domain.Question;
import next.qna.sql.QuestionSql;

@Slf4j
public class QuestionDao {

    private final JdbcTemplate jdbcTemplate = JdbcTemplate.getInstance();

    public QuestionDao() {
        log.info("question dao created");
    }

    public static QuestionDao getInstance() {
        return QuestionDaoHolder.QUESTION_DAO;
    }

    public List<Question> findAll() {

        return jdbcTemplate.query(FindAll,
                preparedStatement -> {
                },
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
                preparedStatement -> preparedStatement.setLong(1, questionId),
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

    public Optional<Question> findOptionalById(long questionId) {
        return Optional.ofNullable(this.findById(questionId));
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

    public void deleteById(long questionId) {
        jdbcTemplate.update(QuestionSql.DELETE_BY_ID, preparedStatement -> {
            preparedStatement.setLong(1, questionId);
        });
    }

    public void update(Question question) {
        jdbcTemplate.update(QuestionSql.UPDATE_QUESTION, preparedStatement -> {
            preparedStatement.setString(1, question.getTitle());
            preparedStatement.setString(2, question.getContents());
            preparedStatement.setLong(3, question.getQuestionId());
        });

    }

    private static class QuestionDaoHolder {
        private static final QuestionDao QUESTION_DAO = new QuestionDao();
    }
}
