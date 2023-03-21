package core.di.factory;


import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.google.common.collect.Sets;
import core.di.ApplicationContext;
import core.di.factory.example.MyFieldQnaService;
import core.di.factory.example.MyQnaService;
import core.di.factory.example.MySetterQnaService;
import core.di.factory.example.QnaController;
import core.di.factory.example.QnaFieldController;
import core.di.factory.example.QnaSetterController;
import java.lang.annotation.Annotation;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;

public class BeanFactoryTest {
    private Reflections reflections;
    private ApplicationContext applicationContext;

    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setup() {
        reflections = new Reflections("core.di.factory.example");
        applicationContext = new ApplicationContext("core.di.factory.example");
    }

    @Test
    public void di() throws Exception {
        QnaController qnaController = applicationContext.getBean(QnaController.class);
        QnaFieldController qnaFieldController = applicationContext.getBean(QnaFieldController.class);
        QnaSetterController qnaSetterController = applicationContext.getBean(QnaSetterController.class);

        // setter inject
        assertNotNull(qnaSetterController);
        assertNotNull(qnaSetterController.getMySetterQnaService());

        // field inject
        assertNotNull(qnaFieldController);
        assertNotNull(qnaFieldController.getMyNewQnaService());

        // constructor inject
        assertNotNull(qnaController);
        assertNotNull(qnaController.getQnaService());

        MyQnaService qnaService = qnaController.getQnaService();
        MyFieldQnaService myFieldQnaService = qnaFieldController.getMyNewQnaService();
        MySetterQnaService mySetterQnaService = qnaSetterController.getMySetterQnaService();

        // setter inject
        assertNotNull(myFieldQnaService.getUserRepository());
        assertNotNull(myFieldQnaService.getQuestionRepository());

        // field inject
        assertNotNull(myFieldQnaService.getUserRepository());
        assertNotNull(myFieldQnaService.getQuestionRepository());

        // constructor inject
        assertNotNull(qnaService.getUserRepository());
        assertNotNull(qnaService.getQuestionRepository());
    }

}
