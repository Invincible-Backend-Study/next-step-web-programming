package core.di.factory;


import static org.junit.jupiter.api.Assertions.assertNotNull;

import core.di.beans.factory.support.DefaultBeanFactory;
import core.di.context.annotation.ClasspathBeanDefinitionScanner;
import core.di.factory.example.MyFieldQnaService;
import core.di.factory.example.MyQnaService;
import core.di.factory.example.MySetterQnaService;
import core.di.factory.example.QnaController;
import core.di.factory.example.QnaFieldController;
import core.di.factory.example.QnaSetterController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DefaultBeanFactoryTest {
    private DefaultBeanFactory defaultBeanFactory;

    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setup() {
        defaultBeanFactory = new DefaultBeanFactory();
        ClasspathBeanDefinitionScanner scanner = new ClasspathBeanDefinitionScanner(defaultBeanFactory);
        scanner.doScan("core.di.factory");
    }

    @Test
    public void di() {
        QnaController qnaController = defaultBeanFactory.getBean(QnaController.class);
        QnaFieldController qnaFieldController = defaultBeanFactory.getBean(QnaFieldController.class);
        QnaSetterController qnaSetterController = defaultBeanFactory.getBean(QnaSetterController.class);

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
