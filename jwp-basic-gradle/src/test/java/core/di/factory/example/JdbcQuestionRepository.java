package core.di.factory.example;

import core.annotation.Inject;
import core.annotation.Repository;
import core.jdbc.JdbcTemplate;

@Repository
public class JdbcQuestionRepository implements QuestionRepository {
    private final JdbcTemplate jdbcTemplate;


    @Inject
    public JdbcQuestionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
