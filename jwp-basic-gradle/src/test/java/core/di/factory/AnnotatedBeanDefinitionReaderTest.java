package core.di.factory;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import core.di.beans.factory.support.DefaultBeanFactory;
import core.di.context.annotation.AnnotatedBeanDefinitionReader;
import core.di.context.annotation.ClasspathBeanDefinitionScanner;
import core.di.factory.example.ExampleConfig;
import core.di.factory.example.IntegrationConfig;
import core.di.factory.example.JdbcUserRepository;
import core.di.factory.example.MyJdbcTemplate;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;

public class AnnotatedBeanDefinitionReaderTest {

    @Test
    void register_simple() {
        DefaultBeanFactory defaultBeanFactory = new DefaultBeanFactory();
        AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(defaultBeanFactory);
        reader.loadBeanDefinitions(ExampleConfig.class);
        defaultBeanFactory.preInstantiateSingletons();
        assertNotNull(defaultBeanFactory.getBean(DataSource.class));
    }

    @Test
    public void register_ClasspathBeanDefinitionScanner_통합() {
        DefaultBeanFactory defaultBeanFactory = new DefaultBeanFactory();
        AnnotatedBeanDefinitionReader abdr = new AnnotatedBeanDefinitionReader(defaultBeanFactory);
        abdr.loadBeanDefinitions(IntegrationConfig.class);

        ClasspathBeanDefinitionScanner cbds = new ClasspathBeanDefinitionScanner(defaultBeanFactory);
        cbds.doScan("core.di.factory.example");

        defaultBeanFactory.preInstantiateSingletons();

        assertNotNull(defaultBeanFactory.getBean(DataSource.class));

        JdbcUserRepository userRepository = defaultBeanFactory.getBean(JdbcUserRepository.class);
        assertNotNull(userRepository);
        assertNotNull(userRepository.getDataSource());

        MyJdbcTemplate jdbcTemplate = defaultBeanFactory.getBean(MyJdbcTemplate.class);
        assertNotNull(jdbcTemplate);
        assertNotNull(jdbcTemplate.getDataSource());
    }
}
