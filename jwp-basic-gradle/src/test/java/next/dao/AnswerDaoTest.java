package next.dao;

import java.util.List;
import next.model.Answer;
import next.model.Question;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AnswerDaoTest {
    AnswerDao answerDao = new AnswerDao();
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

    @Test
    void findByAnswerId() throws Exception {
        Answer answer = answerDao.findByAnswerId(21L);
        System.out.println(answer.toString());
    }

    @Test
    void update() throws Exception {
        Answer answer = answerDao.findByAnswerId(28L);
        System.out.println(answer.toString());
        answer.setContents("content changed!!!");
        answerDao.update(answer);

        answer = answerDao.findByAnswerId(28L);
        System.out.println(answer.toString());
    }
}
