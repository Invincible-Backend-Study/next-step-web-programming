package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import next.exception.DataAccessException;

public class JdbcTemplate {

    private static JdbcTemplate instance;

    private JdbcTemplate() {
    }

    public static JdbcTemplate getInstance() {
        if (instance == null) {
            instance = new JdbcTemplate();
        }
        return instance;
    }

    public int update(final String query, final PreparedStatementSetter preparedStatementSetter) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatementSetter.setValue(preparedStatement);
            return preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            throw new DataAccessException(exception);
        }
    }

    public int update(final String query, final Object... values) {
        return update(query, createPreparedStatementSetter(values));
    }

    public int update(final PreparedStatementCreator preparedStatementCreator, final KeyHolder keyHolder) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = preparedStatementCreator.createPreparedStatement(connection)) {
            int result = preparedStatement.executeUpdate();

            try (ResultSet keyResultSet = preparedStatement.getGeneratedKeys()) {
                if (keyResultSet.next()) {
                    keyHolder.setId(keyResultSet.getLong(1));
                }
            }
            return result;
        } catch (SQLException exception) {
            throw new DataAccessException(exception);
        }
    }

    public <T> List<T> query(
            final String query,
            final RowMapper<T> rowMapper,
            final PreparedStatementSetter preparedStatementSetter) {

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatementSetter.setValue(preparedStatement);
            List<T> results = new ArrayList<>();

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    results.add(rowMapper.mapRow(resultSet));
                }
            }
            return results;
        } catch (SQLException exception) {
            throw new DataAccessException(exception);
        }
    }

    public <T> List<T> query(final String query, final RowMapper<T> rowMapper, final Object... values) {
        return query(query, rowMapper, createPreparedStatementSetter(values));
    }

    public <T> T queryForObject(
            final String query,
            final RowMapper<T> rowMapper,
            final PreparedStatementSetter preparedStatementSetter) {
        List<T> results = query(query, rowMapper, preparedStatementSetter);
        if (results.isEmpty()) {
            return null;
        }
        return results.get(0);
    }

    public <T> T queryForObject(
            final String query,
            final RowMapper<T> rowMapper,
            final Object... values) {
        return queryForObject(query, rowMapper, createPreparedStatementSetter(values));
    }

    private PreparedStatementSetter createPreparedStatementSetter(final Object[] values) {
        return preparedStatement -> {
            for (int count = 1; count <= values.length; count++) {
                preparedStatement.setObject(count, values[count - 1]);
            }
        };
    }

}
