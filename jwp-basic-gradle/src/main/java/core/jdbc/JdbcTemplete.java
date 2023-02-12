package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTemplete {
    public void insert(String sql, PreparedStatementSetter pss) throws SQLException {
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pss.setParameters(pstmt);
            pstmt.executeUpdate();
        }
    }

    public <T> T find(String sql, RawMapper<T> rm, PreparedStatementSetter pss) throws SQLException {
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pss.setParameters(pstmt);
            return getResultSet(rm, pstmt);
        }
    }

    private static <T> T getResultSet(RawMapper<T> rm, PreparedStatement pstmt) throws SQLException {
        try (ResultSet rs = pstmt.executeQuery()) {
            return rm.mapRow(rs);
        }
    }


}
