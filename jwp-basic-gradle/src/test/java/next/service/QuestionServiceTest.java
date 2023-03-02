package next.service;

import static org.mockito.BDDMockito.given;

import java.util.Date;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Question;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


class QuestionServiceTest {

    QuestionService questionService;

    QuestionDao questionDao = Mockito.mock(QuestionDao.class);

    AnswerDao answerDao = Mockito.mock(AnswerDao.class);

    @BeforeEach
    void setUp() {
//        questionService = new QuestionService(questionDao, answerDao);
    }

    @Test
    void test() {
        System.out.println(questionService);
        given(questionDao.findById(1L)).willReturn(new Question(1L, "test", "test", "test", new Date(), 0));

        Question byId = questionDao.findById(1L);

        Assertions.assertThat(byId.getQuestionId()).isEqualTo(1L);
    }
}