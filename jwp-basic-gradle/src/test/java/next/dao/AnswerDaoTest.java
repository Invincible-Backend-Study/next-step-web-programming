package next.dao;

import java.util.List;

import next.api.qna.dao.AnswerDao;
import next.api.qna.dao.QuestionDao;
import next.api.qna.model.Answer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AnswerDaoTest {
    private final AnswerDao answerDao= new AnswerDao();

    @BeforeEach
    void beforeEach() throws Exception {
        Answer q1 = new Answer("john", "helloworld", 1L);
        Answer q2 = new Answer("park", "byeworld", 1L);
        Answer q3 = new Answer("nick", "hiworld", 2L);

        answerDao.insert(q1);
        answerDao.insert(q2);
        answerDao.insert(q3);
    }

    @AfterEach
    void afterEach() throws Exception {
        answerDao.deleteAll();
    }

    @Test
    void findAll() throws Exception {
        List<Answer> answers = answerDao.findAll();
        System.out.println(answers.toString());
    }
}
