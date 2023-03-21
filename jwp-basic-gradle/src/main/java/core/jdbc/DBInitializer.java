package core.jdbc;

import core.annotation.Component;
import core.annotation.Inject;
import core.annotation.PostConstruct;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Slf4j
@Component
public class DBInitializer {

    @Inject
    private DataSource dataSource;

    @PostConstruct
    public void initialize() {
        final var resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("jwp.sql"));

        DatabasePopulatorUtils.execute(resourceDatabasePopulator, dataSource);

        log.info("Completed Load ServletContext");
    }
}
