package next.dao;

import core.annotation.Repository;
import core.jdbc.JdbcTemplate;
import java.sql.Timestamp;
import java.util.List;
import next.model.Question;

@Repository
public class JdbcQuestionDao implements QuestionDao {

    private static final JdbcTemplate jdbcTemplate = JdbcTemplate.getInstance();

    public List<Question> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM QUESTIONS",
                resultSet -> new Question(
                        resultSet.getLong("questionId"),
                        resultSet.getString("writer"),
                        resultSet.getString("title"),
                        resultSet.getString("contents"),
                        resultSet.getTimestamp("createdDate"),
                        resultSet.getInt("countOfAnswer"))
        );
    }

    public Question findById(final long questionId) {
        return jdbcTemplate.queryForObject(
                "SELECT "
                        + "questionId, writer, title, contents, createdDate, countOfAnswer "
                        + "FROM QUESTIONS "
                        + "where questionId = ?",
                resultSet -> new Question(
                        resultSet.getLong("questionId"),
                        resultSet.getString("writer"),
                        resultSet.getString("title"),
                        resultSet.getString("contents"),
                        resultSet.getTimestamp("createdDate"),
                        resultSet.getInt("countOfAnswer")),
                questionId
        );
    }

    public List<Question> findAllOrderByCreatedDate() {
        return jdbcTemplate.query(
                "SELECT * FROM QUESTIONS ORDER BY createdDate desc",
                resultSet -> new Question(
                        resultSet.getLong("questionId"),
                        resultSet.getString("writer"),
                        resultSet.getString("title"),
                        resultSet.getString("contents"),
                        resultSet.getTimestamp("createdDate"),
                        resultSet.getInt("countOfAnswer"))
        );
    }

    public void insertNewQuestion(final Question question) {
        jdbcTemplate.update(
                "INSERT INTO"
                        + " QUESTIONS(writer, title, contents, createdDate, countOfAnswer)"
                        + " VALUES(?, ?, ?, ?, ?)",
                question.getWriter(),
                question.getTitle(),
                question.getContents(),
                new Timestamp(question.getTimeFromCreatedDate()),
                question.getCountOfAnswer());
    }

    public void updateCountOfAnswerById(final Long questionId, final int countOfAnswer) {
        jdbcTemplate.update(
                "UPDATE QUESTIONS SET countOfAnswer = ? WHERE questionId = ?",
                countOfAnswer, questionId);
    }

    public int update(final Question question) {
        return jdbcTemplate.update(
                "UPDATE QUESTIONS SET title = ?, contents = ? WHERE questionId = ?",
                question.getTitle(), question.getContents(), question.getQuestionId()
        );
    }

    public int deleteById(final Long questionId) {
        return jdbcTemplate.update(
                "DELETE FROM QUESTIONS WHERE questionId = ?",
                questionId
        );
    }
}
