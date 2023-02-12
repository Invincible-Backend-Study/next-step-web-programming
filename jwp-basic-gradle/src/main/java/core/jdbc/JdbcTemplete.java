package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTemplete {
    public void insert(String sql, Object... parameters) throws SQLException {
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            setParameters(pstmt, parameters);
            pstmt.executeUpdate();
        }
    }


    public <T> T find(String sql, RawMapper<T> rm, Object... parameters) throws SQLException {
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            setParameters(pstmt, parameters);
            return getResultSet(rm, pstmt);
        }
    }

    private static <T> T getResultSet(RawMapper<T> rm, PreparedStatement pstmt) throws SQLException {
        try (ResultSet rs = pstmt.executeQuery()) {
            return rm.mapRow(rs);
        }
    }

    private static void setParameters(PreparedStatement pstmt, Object[] parameters) throws SQLException {
        for (int i = 0; i < parameters.length; i++) {
            pstmt.setObject(i + 1, parameters[i]);
        }
    }


}
