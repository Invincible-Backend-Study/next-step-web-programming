package core.jdbc;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTemplate {
    public int executeUpdate(String sql, PreparedStatementParameters ps) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        int countChanged = 0;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            ps.setParameters(pstmt);

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

    public Object select(String sql, PreparedStatementParameters ps, ResultSetMapper resultSetMapper) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Object result = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            ps.setParameters(pstmt);

            rs = pstmt.executeQuery();

            result = resultSetMapper.mapRow(rs);
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
}
