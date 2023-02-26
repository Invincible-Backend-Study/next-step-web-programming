package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTemplate {
    private static JdbcTemplate jdbcTemplate = new JdbcTemplate();
    private JdbcTemplate();
    public static JdbcTemplate getInstance() {
        return jdbcTemplate;
    }

    public long insert(String sql, PreparedStatementParameters ps) {
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            ps.setParameters(pstmt);
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.toString());
        }
        return 0;
    }

    public long insert(String sql, Object... parameters) {
        PreparedStatementParameters ps = getPreparedStatementParameters(parameters);
        return insert(sql, ps);
    }

    public int executeUpdate(String sql, PreparedStatementParameters ps) {
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            ps.setParameters(pstmt);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e.toString());
        }
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
            throw new DataAccessException(e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
        return result;
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
