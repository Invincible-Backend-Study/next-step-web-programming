package core.example;

import core.di.factory.BeanFactory;
import core.di.factory.ExampleConfig;
import javax.sql.DataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AnnotatedBeanDefinitionReaderTest {

    @Test
    public void register_simple() {
        BeanFactory beanFactory = new BeanFactory();

        final var annotatedBeanDefinitionReader = new AnnotatedBeanDefinitionReader(beanFactory);
        annotatedBeanDefinitionReader.register(ExampleConfig.class);

        beanFactory.initialize();

        Assertions.assertNotNull(beanFactory.getBean(DataSource.class));

    }
}
