package next.dao;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import next.model.Answer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class AnswerDaoTest {
    private static final Logger log = LoggerFactory.getLogger(AnswerDaoTest.class);

    AnswerDao answerDao;

    @BeforeEach
    void init() {
        answerDao = new AnswerDao();
    }

    @Test
    void findAllByQuestionId() {
        List<Answer> answers = answerDao.findAllByQuestionId(7L);
        log.info("answers = {}", answers);
    }

    @Test
    void insert() {
        // given
        Answer answer = new Answer("호석", "test", 1L);

        // when
        Answer insertAnswer = answerDao.insert(answer);

        // then
        Assertions.assertAll(
                () -> assertThat(insertAnswer.getAnswerId()).isNotNull(),
                () -> assertThat(insertAnswer.getWriter()).isEqualTo(answer.getWriter())
        );
    }
}