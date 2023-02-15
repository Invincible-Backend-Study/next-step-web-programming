package next.dao;

import core.jdbc.JdbcTemplate;
import java.util.List;
import next.model.Question;

public class QuestionDao {
    private final JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public List<Question> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM QUESTIONS",
                resultSet -> new Question(
                        resultSet.getLong("questionId"),
                        resultSet.getString("writer"),
                        resultSet.getString("title"),
                        resultSet.getString("contents"),
                        resultSet.getDate("createdDate"),
                        resultSet.getInt("countOfAnswer"))
        );
    }

    public Question findByQuestionId(final long questionId) {
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
                        resultSet.getDate("createdDate"),
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
                        resultSet.getDate("createdDate"),
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
                question.getCreatedDate(),
                question.getCountOfAnswer());
    }
}
