package next.dao;

import java.util.List;
import next.model.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class QuestionDaoTest {
    private static final Logger log = LoggerFactory.getLogger(QuestionDaoTest.class);

    QuestionDao questionDao;

    @BeforeEach
    void init() {
        questionDao = new QuestionDao();
    }

    @Test
    void findAll() {
        List<Question> questions = questionDao.findAll();
        log.info("questions = {}", questions);
    }

    @Test
    void findAllOrderByCreatedDate() {
        List<Question> questions = questionDao.findAllOrderByCreatedDate();
        log.info("questions = {}", questions);
    }

    @Test
    void findByQuestionId() {
        Question question = questionDao.findByQuestionId(1L);
        log.info("question = {}", question);
    }
}