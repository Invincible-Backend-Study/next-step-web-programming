package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTemplate {
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
        PreparedStatementParameters ps = getPreparedStatementParameters(parameters);
        return executeUpdate(sql, ps);
    }

    public <T> T select(String sql, ResultSetMapper<T> resultSetMapper, PreparedStatementParameters ps) throws SQLException {
        ResultSet rs = null;
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
        PreparedStatementParameters ps = getPreparedStatementParameters(null);
        return select(sql, resultSetMapper, ps);
    }

    public <T> T select(String sql, ResultSetMapper<T> resultSetMapper, Object... parameters) throws SQLException {
        PreparedStatementParameters ps = getPreparedStatementParameters(parameters);
        return select(sql, resultSetMapper, ps);
    }

    private static PreparedStatementParameters getPreparedStatementParameters(Object[] parameters) {
        return pstmt -> {
            if (parameters == null) {
                return;
            }
            for (int i = 0; i < parameters.length; i++) {
                pstmt.setObject(i + 1, parameters[i]);
            }
        };
    }
}
