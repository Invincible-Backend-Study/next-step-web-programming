package core.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp2.BasicDataSource;


@Slf4j
public class ConnectionManager {
    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String POSTGRESQL_DRIVER = "org.postgresql.Driver";
    private static final String POSTGRESQL_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String DB_URL = "jdbc:h2:~/jwp-basic;AUTO_SERVER=TRUE";
    private static final String POSTGRESQL_USERNAME = "postgres";
    private static final String DB_USERNAME = "sa";
    private static final String POSTGRESQL_PASSWORD = "1234";
    private static final String DB_PW = "";

    public static final Set<Integer> connectionCodes = new HashSet<>();

    private static final DataSource dataSource;

    static {
        BasicDataSource ds = new BasicDataSource();
        /*ds.setDriverClassName(DB_DRIVER);
        ds.setUrl(DB_URL);
        ds.setUsername(DB_USERNAME);
        ds.setPassword(DB_PW);*/

        ds.setMaxIdle(12);
        ds.setPoolPreparedStatements(true);
        ds.setDriverClassName(POSTGRESQL_DRIVER);
        ds.setUrl(POSTGRESQL_URL);
        ds.setUsername(POSTGRESQL_USERNAME);
        ds.setPassword(POSTGRESQL_PASSWORD);

        dataSource = ds;
        log.info("{}", ds);
    }
    public static DataSource getDataSource() {
        return dataSource;
    }

    public static Connection getConnection() {
        try {
            var connection = dataSource.getConnection();
            connectionCodes.add(connection.hashCode());
            return connection;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
