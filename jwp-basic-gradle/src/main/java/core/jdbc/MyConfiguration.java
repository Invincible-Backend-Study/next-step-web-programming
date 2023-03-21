package core.jdbc;

import core.annotation.Bean;
import core.annotation.ComponentScan;
import core.annotation.Configuration;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;

@Configuration
@ComponentScan({"next", "core"})
public class MyConfiguration {
    private static final String POSTGRESQL_DRIVER = "org.postgresql.Driver";
    private static final String POSTGRESQL_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String POSTGRESQL_USERNAME = "postgres";
    private static final String POSTGRESQL_PASSWORD = "1234";


    @Bean
    public DataSource dataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setMaxIdle(12);
        ds.setPoolPreparedStatements(true);
        ds.setDriverClassName(POSTGRESQL_DRIVER);
        ds.setUrl(POSTGRESQL_URL);
        ds.setUsername(POSTGRESQL_USERNAME);
        ds.setPassword(POSTGRESQL_PASSWORD);
        return ds;
    }


}
