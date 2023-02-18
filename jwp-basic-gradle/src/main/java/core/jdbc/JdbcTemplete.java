package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcTemplete {
    public void excuteSqlUpdate(String sql, Object... parameters) throws SQLException {
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            setSqlParameters(pstmt, parameters);
            pstmt.executeUpdate();
        }
    }


    public <T> T excuteFindStaticData(String sql, RawMapper<T> rm) throws SQLException {
        try (Connection con = ConnectionManager.getConnection(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(
                sql);) {
            return rm.mapRow(rs);
        }
    }

    public <T> T excuteFindDynamicData(String sql, RawMapper<T> rm, Object... parameters) throws SQLException {
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            setSqlParameters(pstmt, parameters);
            return getResultSet(rm, pstmt);
        }
    }

//    public <T> T excuteFindDataTest(String sql, RawMapper<T> rm, Object... parameters) throws SQLException {
//        try (Connection con = ConnectionManager.getConnection(); CallableStatement cstmt = con.prepareCall(sql);) {
//            setSqlParameters(cstmt, parameters);
//            return getResultSet(rm, cstmt);
//        }
//    }

    private static <T> T getResultSet(RawMapper<T> rm, PreparedStatement pstmt) throws SQLException {
        try (ResultSet rs = pstmt.executeQuery()) {
            return rm.mapRow(rs);
        }
    }


    private static void setSqlParameters(PreparedStatement pstmt, Object[] parameters) throws SQLException {
        for (int i = 0; i < parameters.length; i++) {
            pstmt.setObject(i + 1, parameters[i]);
        }
    }


}
