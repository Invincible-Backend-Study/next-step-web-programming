package core.jdbc;

import java.util.Collection;
import javax.sql.DataSource;

public interface Connection {
    DataSource getDataSource();
    java.sql.Connection getConnection();
}
