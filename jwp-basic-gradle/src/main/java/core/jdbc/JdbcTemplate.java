package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class JdbcTemplate {
    public int executeUpdate(String sql) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        int countChanged = 0;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            setParameters(pstmt);

            countChanged = pstmt.executeUpdate();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            if (con != null) {
                con.close();
            }
        }
        return countChanged;
    }

    public abstract void setParameters(PreparedStatement pstmt) throws SQLException;
}
