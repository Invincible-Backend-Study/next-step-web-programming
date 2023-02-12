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

    public Object find(String sql,RawMapper rm) throws SQLException {
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            Object data = null;
            data = rm.mapRow(rs);
            return data;
        }
    }


}
