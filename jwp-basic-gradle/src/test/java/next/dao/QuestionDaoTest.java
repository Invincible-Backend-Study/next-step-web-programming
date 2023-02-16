package next.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;
import next.model.Question;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QuestionDaoTest {
    QuestionDao questionDao = new QuestionDao();
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

    @Test
    void findByQuestionId() throws Exception {
        Question question = questionDao.findByQuestionId(33L);
        System.out.println(question.toString());
    }

    @Test
    void update() throws Exception {
        Question question = questionDao.findByQuestionId(39L);
        System.out.println(question.toString());
        question.setTitle("newTitle!!");
        questionDao.update(question);

        question = questionDao.findByQuestionId(39L);
        System.out.println(question.toString());
    }
}
