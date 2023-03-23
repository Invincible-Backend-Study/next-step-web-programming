package core.di.factory;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import core.di.factory.example.ExampleConfig;
import core.di.factory.example.IntegrationConfig;
import core.di.factory.example.JdbcUserRepository;
import core.di.factory.example.MyJdbcTemplate;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;

public class AnnotatedBeanDefinitionReaderTest {

    @Test
    void register_simple() {
        BeanFactory beanFactory = new BeanFactory();
        AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(beanFactory);
        reader.register(ExampleConfig.class);
        beanFactory.initialize();
        assertNotNull(beanFactory.getBean(DataSource.class));
    }

    @Test
    public void register_ClasspathBeanDefinitionScanner_통합() {
        BeanFactory beanFactory = new BeanFactory();
        AnnotatedBeanDefinitionReader abdr = new AnnotatedBeanDefinitionReader(beanFactory);
        abdr.register(IntegrationConfig.class);

        ClasspathBeanDefinitionScanner cbds = new ClasspathBeanDefinitionScanner(beanFactory);
        cbds.doScan("core.di.factory.example");

        beanFactory.initialize();

        assertNotNull(beanFactory.getBean(DataSource.class));

        JdbcUserRepository userRepository = beanFactory.getBean(JdbcUserRepository.class);
        assertNotNull(userRepository);
        assertNotNull(userRepository.getDataSource());

        MyJdbcTemplate jdbcTemplate = beanFactory.getBean(MyJdbcTemplate.class);
        assertNotNull(jdbcTemplate);
        assertNotNull(jdbcTemplate.getDataSource());
    }
}
