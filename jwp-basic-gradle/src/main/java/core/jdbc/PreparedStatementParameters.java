package core.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PreparedStatementParameters {
    void setParameters(PreparedStatement pstmt) throws SQLException;
}
