package next.dao;

import core.di.context.support.AnnotationConfigApplicationContext;
import core.di.context.ApplicationContext;
import javax.sql.DataSource;
import next.config.MyConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

public class DaoTest {

    ApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        applicationContext = new AnnotationConfigApplicationContext(MyConfiguration.class);
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("jwp.sql"));
        DatabasePopulatorUtils.execute(populator, applicationContext.getBean(DataSource.class));

    }
}
