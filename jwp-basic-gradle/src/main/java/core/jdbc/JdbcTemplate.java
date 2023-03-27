package core.jdbc;

import core.annotation.Component;
import core.annotation.Inject;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class JdbcTemplate {
    private DataSource dataSource;

    public JdbcTemplate(DataSource dataSource) {
        super();
        this.dataSource = dataSource;
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

    public <T> T select(String sql, ResultSetMapper<T> resultSetMapper, PreparedStatementParameters ps) {
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            ps.setParameters(pstmt);

            try (ResultSet rs = pstmt.executeQuery()) {
                return resultSetMapper.mapRow(rs);
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.toString());
        }
    }

    public <T> T select(String sql, ResultSetMapper<T> resultSetMapper, Object... parameters) {
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
