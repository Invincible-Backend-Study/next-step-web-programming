package core.jdbc;

import next.exception.DataAcessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcTemplete {

    private static JdbcTemplete instance = null;

    private JdbcTemplete() {}

    public static synchronized JdbcTemplete getInstance() {
        if (instance == null) {
            instance = new JdbcTemplete();
        }
        return instance;
    }
    public void excuteSqlUpdate(String sql, Object... parameters) {
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            setSqlParameters(pstmt, parameters);
            pstmt.executeUpdate();
        }catch (SQLException e){
            throw new DataAcessException(e);
        }
    }

    public void excuteSqlUpdate(KeyHolder key, String sql, Object ...parameters){
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            setSqlParameters(pstmt, parameters);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if(rs.next()) {
                key.setId(rs.getInt(1));
            }
            rs.close();
        }catch (SQLException e){
            throw new DataAcessException(e);
        }
    }


    public <T> T excuteFindStaticData(String sql, RawMapper<T> rm) {
        try (Connection con = ConnectionManager.getConnection(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(
                sql);) {
            return rm.mapRow(rs);
        }catch (SQLException e){
            throw new DataAcessException(e);
        }
    }

    public <T> T excuteFindDynamicData(String sql, RawMapper<T> rm, Object... parameters) {
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            setSqlParameters(pstmt, parameters);
            return getResultSet(rm, pstmt);
        }catch (SQLException e){
            throw new DataAcessException(e);
        }
    }

//    public <T> T excuteFindDataTest(String sql, RawMapper<T> rm, Object... parameters) throws SQLException {
//        try (Connection con = ConnectionManager.getConnection(); CallableStatement cstmt = con.prepareCall(sql);) {
//            setSqlParameters(cstmt, parameters);
//            return getResultSet(rm, cstmt);
//        }
//    }

    private static <T> T getResultSet(RawMapper<T> rm, PreparedStatement pstmt) {
        try (ResultSet rs = pstmt.executeQuery()) {
            return rm.mapRow(rs);
        }catch (SQLException e){
            throw new DataAcessException(e);
        }
    }


    private static void setSqlParameters(PreparedStatement pstmt, Object[] parameters) throws SQLException {
        for (int i = 0; i < parameters.length; i++) {
            pstmt.setObject(i + 1, parameters[i]);
        }
    }


}
