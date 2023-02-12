package core.jdbc;

import next.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class JdbcTemplete {
    public void insert(String sql) throws SQLException {
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            setParameters(pstmt);
            pstmt.executeUpdate();
        }
    }


    abstract public void setParameters(PreparedStatement pstsmt) throws SQLException;
}
