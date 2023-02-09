package core.jdbc;

import next.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class SelectJdbcTemplate {

    public Object select(String sql) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Object result = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);

            rs = pstmt.executeQuery();

            result = mapRow(rs);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }

    public abstract void setParameters(PreparedStatement pstmt) throws SQLException;

    public abstract Object mapRow(ResultSet rs) throws SQLException;
}
