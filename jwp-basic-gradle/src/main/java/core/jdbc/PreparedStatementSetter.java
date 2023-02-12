package core.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface PreparedStatementSetter {
    void setParameters(PreparedStatement pstmt) throws SQLException;

}
