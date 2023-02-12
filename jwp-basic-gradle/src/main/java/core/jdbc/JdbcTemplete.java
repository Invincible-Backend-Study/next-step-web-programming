package core.jdbc;

import next.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class JdbcTemplete {
    public void insert(String sql) throws SQLException {
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            setParameters(pstmt);
            pstmt.executeUpdate();
        }
    }

    public Object find(String sql) throws SQLException {
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            Object user = null;
            user = mapRow(rs);
            return user;
        }
    }

    abstract public Object mapRow(ResultSet rs) throws SQLException;


    abstract public void setParameters(PreparedStatement pstmt) throws SQLException;
}
