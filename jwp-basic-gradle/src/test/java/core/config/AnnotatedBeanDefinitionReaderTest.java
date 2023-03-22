package core.config;

import core.di.AnnotatedBeanDefinitionReader;
import core.di.BeanFactory;
import javax.sql.DataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AnnotatedBeanDefinitionReaderTest {

    @Test
    void register_simple() {
        BeanFactory beanFactory = new BeanFactory();
        AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(beanFactory);
        reader.register(ExampleConfig.class);
        beanFactory.initialize();
        Assertions.assertNotNull(beanFactory.getBean(DataSource.class));
    }
}
