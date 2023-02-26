package core.jdbc;

import static core.jdbc.ConnectionManager.getConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class JdbcTemplate {

    public static JdbcTemplate getInstance() {
        return JdbcTemplateHolder.JDBC_TEMPLATE;
    }

    public int update(String sql, PreparedStatementSetter preparedStatementSetter) {
        try (final var connection = ConnectionManager.getConnection(); final var preparedStatement = connection.prepareStatement(sql)) {
            log.info("{}", sql);

            preparedStatementSetter.setValues(preparedStatement);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public void update(PreparedStatementCreator psc, KeyHolder holder) {
        try (final var conn = ConnectionManager.getConnection()) {
            PreparedStatement ps = psc.createPreparedStatement(conn);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                holder.setId(rs.getLong(1));
            }
            rs.close();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    private <T> List<T> resultSetToMapper(RowMapper<T> rowMapper, PreparedStatement preparedStatement) throws SQLException {
        try (final var resultSet = preparedStatement.executeQuery()) {
            final var result = new ArrayList<T>();

            while (resultSet.next()) {
                result.add(rowMapper.mapRow(resultSet));
            }
            return result;
        }
    }

    public <T> List<T> query(String sql, PreparedStatementSetter preparedStatementSetter, RowMapper<T> rowMapper) throws DataAccessException {
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(sql)) {

            log.info("{}", sql);

            preparedStatementSetter.setValues(preparedStatement);
            return resultSetToMapper(rowMapper, preparedStatement);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public <T> T queryForObject(String sql, PreparedStatementSetter preparedStatementSetter, RowMapper<T> rowMapper) throws DataAccessException {
        final var result = this.query(sql, preparedStatementSetter, rowMapper);
        return result.isEmpty() ? null : result.get(0);
    }

    private static class JdbcTemplateHolder {
        private static final JdbcTemplate JDBC_TEMPLATE = new JdbcTemplate();
    }
}
