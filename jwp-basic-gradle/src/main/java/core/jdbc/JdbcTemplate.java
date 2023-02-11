package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import next.exception.DataAccessException;
import next.model.User;

public abstract class JdbcTemplate {
    public void update(final User user) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = ConnectionManager.getConnection();
            String sql = createQuery();
            pstmt = con.prepareStatement(sql);
            setValue(user, pstmt);
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

    protected abstract void setValue(final User user, final PreparedStatement pstmt) throws SQLException;

    protected abstract String createQuery();
}
