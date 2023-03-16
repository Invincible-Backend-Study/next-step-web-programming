package next.dao;

import java.util.List;

import next.api.qna.dao.QuestionDao;
import next.api.qna.model.Question;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QuestionDaoTest {
    private final QuestionDao questionDao = new QuestionDao();
    @BeforeEach
    void beforeEach() throws Exception {
        Question q1 = new Question("john", "helloworld", "this is contents");
        Question q2 = new Question("park", "byeworld", "this is contents22");
        Question q3 = new Question("nick", "hiworld", "this is contents33");

        questionDao.insert(q1);
        questionDao.insert(q2);
        questionDao.insert(q3);
    }

    @AfterEach
    void afterEach() throws Exception {
        questionDao.deleteAll();
    }

    @Test
    void findAll() throws Exception {
        List<Question> questions = questionDao.findAll();
        System.out.println(questions.toString());
    }
}
