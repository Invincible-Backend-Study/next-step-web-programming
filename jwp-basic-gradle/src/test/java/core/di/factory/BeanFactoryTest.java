package core.di.factory;

import static org.junit.Assert.assertNotNull;

import core.di.factory.example.ExampleController;
import core.di.factory.example.ExampleService;
import java.lang.annotation.Annotation;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.reflections.Reflections;

import com.google.common.collect.Sets;

import core.di.factory.example.MyQnaService;
import core.di.factory.example.QnaController;

public class BeanFactoryTest {
    private BeanFactory beanFactory;

    @Before
    public void setup() {
        beanFactory = new BeanFactory();
        ClasspathBeanDefinitionScanner beanScanner = new ClasspathBeanDefinitionScanner(beanFactory);
        beanScanner.doScan("core.di.factory.example");
        beanFactory.initialize();
    }

    @Test
    public void di() throws Exception {
        QnaController qnaController = beanFactory.getBean(QnaController.class);

        assertNotNull(qnaController);
        assertNotNull(qnaController.getQnaService());

        MyQnaService qnaService = qnaController.getQnaService();
        assertNotNull(qnaService.getUserRepository());
        assertNotNull(qnaService.getQuestionRepository());
    }

    @Test
    public void diExample() throws Exception {
        ExampleController exampleController = beanFactory.getBean(ExampleController.class);
        assertNotNull(exampleController);
        assertNotNull(exampleController.getExampleService());

        ExampleService exampleService = exampleController.getExampleService();
        assertNotNull(exampleService.getExampleRepository());

        assertNotNull(exampleController.findExample());
        System.out.println(exampleController.findExample());
    }
}
