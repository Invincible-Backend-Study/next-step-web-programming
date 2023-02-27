package next.dao;

import core.jdbc.JdbcTemplate;
import core.jdbc.KeyHolder;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import next.answer.dao.AnswerDao;
import next.answer.model.Answer;
import next.dao.template.DataTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class AnswerDaoTest extends DataTest {

    private final AnswerDao answerDao = new AnswerDao();
    private final JdbcTemplate jdbcTemplate = JdbcTemplate.getInstance();

    @Test
    void 데이터가_정상적으로_생성되는가() {
        var data = answerDao.insert(new Answer("1234", "1234", 1L));

        org.assertj.core.api.Assertions.assertThat(data.getWriter()).isEqualTo("1234");
    }


    @Test
    public void KEY_HOLDER_적용_테스트() {

        final var keyHolder = new KeyHolder(); // key holder

        final var answer = new Answer("1234", "1234", 1L); // 테스트용 answer 객체
        jdbcTemplate.update(psc -> {
            final var preparedStatement = psc.prepareStatement("INSERT INTO answers(writer, contents, createddate, questionid) VALUES (?,?,?,?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, answer.getWriter());
            preparedStatement.setString(2, answer.getContents());
            preparedStatement.setTimestamp(3, new Timestamp(answer.getTimeFromCreateDate()));
            preparedStatement.setLong(4, answer.getQuestionId());
            return preparedStatement;
        }, keyHolder);

        Assertions.assertThat(keyHolder.getId()).isNotEqualTo(0L);
    }

    @Test
    void postgresql_serial_에러() {
        final var answer = new Answer("1234", "1234", 1L); // 테스트용 answer 객체
        final var savedAnswer = answerDao.insert(answer); // id 6

        final var sql = "INSERT INTO QUESTIONS (questionId, writer, title, contents, createdDate, countOfAnswer) VALUES\n"
                + "?,?,?,?,?,?";
        jdbcTemplate.update(sql, (preparedStatement -> {
            preparedStatement.setLong(1, savedAnswer.getAnswerId() + 1); // 7
            preparedStatement.setString(2, answer.getWriter());
            preparedStatement.setString(3, answer.getContents());
            preparedStatement.setTimestamp(4, new Timestamp(answer.getTimeFromCreateDate()));
            preparedStatement.setLong(5, answer.getQuestionId());
        }));
        answerDao.insert(answer); // 8
    }
}