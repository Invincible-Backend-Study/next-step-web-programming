package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import next.exception.DataAccessException;

public abstract class JdbcTemplate {
    public void update(final String query) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(query);
            setValue(pstmt);
            pstmt.executeUpdate();
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException exception) {
            throw new DataAccessException(exception.getMessage());
        }
    }

    protected abstract void setValue(final PreparedStatement pstmt) throws SQLException;

}