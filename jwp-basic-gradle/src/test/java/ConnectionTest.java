import java.sql.Connection;
import java.sql.SQLException;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionTest {
    private static final Logger log = LoggerFactory.getLogger(ConnectionTest.class);

    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:~/jwp-basic;AUTO_SERVER=TRUE";
    private static final String DB_USERNAME = "sa";
    private static final String DB_PW = "";

    @Test
    void dataSourceConnectionPool() throws SQLException, InterruptedException {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(DB_DRIVER);
        ds.setUrl(DB_URL);
        ds.setUsername(DB_USERNAME);
        ds.setPassword(DB_PW);
//        ds.setMaxWaitMillis(3000);
        for (int i = 0; i < 100; i++) {
            useDataSource(ds);
        }
        log.info("active connection num : {}", ds.getNumActive());
    }

    private void useDataSource(final BasicDataSource ds) throws SQLException, InterruptedException {
        Connection connection1 = ds.getConnection();
        Connection connection2 = ds.getConnection();
        if (ds.getNumActive() == 8) {
            connection2.close();
        }

        log.info("connection : {}, class = {}, hashCode={}", connection1, connection1.getClass(),
                connection1.hashCode());
        log.info("connection : {}, class = {}, hashCode={}", connection2, connection2.getClass(),
                connection2.hashCode());
//        Thread.sleep(1000);
//        connection1.close();
//        connection2.close();
    }
}
