package core.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface PreparedStatementParameters {
    void setParameters(PreparedStatement pstmt) throws SQLException;
}
