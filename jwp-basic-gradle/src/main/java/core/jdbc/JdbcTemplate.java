package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTemplate {
    public int executeUpdate(String sql) {
        int countChanged = 0;
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            countChanged = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e.toString());
        }
        return countChanged;
    }

    public int executeUpdate(String sql, PreparedStatementParameters ps) {
        int countChanged = 0;
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            ps.setParameters(pstmt);
            countChanged = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e.toString());
        }
        return countChanged;
    }

    public int executeUpdate(String sql, Object... parameters) {
        int countChanged = 0;
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            for (int i = 0; i < parameters.length; i++) {
                pstmt.setObject(i + 1, parameters[i]);
            }
            countChanged = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e.toString());
        }
        return countChanged;
    }

    public <T> T select(String sql, ResultSetMapper<T> resultSetMapper, PreparedStatementParameters ps) throws SQLException {
        ResultSet rs = null;  //TODO rs도 빼는 방법?
        T result = null;
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            ps.setParameters(pstmt);
            rs = pstmt.executeQuery();
            result = resultSetMapper.mapRow(rs);
        } catch (SQLException e) {
            throw new DataAccessException();
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
        return result;
    }

    public <T> T select(String sql, ResultSetMapper<T> resultSetMapper) throws SQLException {
        ResultSet rs = null;  //TODO rs도 빼는 방법?
        T result = null;
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            rs = pstmt.executeQuery();
            result = resultSetMapper.mapRow(rs);
        } catch (SQLException e) {
            throw new DataAccessException();
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
        return result;
    }

    public <T> T select(String sql, ResultSetMapper<T> resultSetMapper, Object... parameters) throws SQLException {
        ResultSet rs = null;  //TODO rs도 빼는 방법?
        T result = null;
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            for (int i = 0; i < parameters.length; i++) {
                pstmt.setObject(i + 1, parameters[i]);
            }
            rs = pstmt.executeQuery();
            result = resultSetMapper.mapRow(rs);
        } catch (SQLException e) {
            throw new DataAccessException();
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
        return result;
    }
}
