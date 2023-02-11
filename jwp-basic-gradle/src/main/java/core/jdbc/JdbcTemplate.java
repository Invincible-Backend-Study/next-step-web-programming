package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import next.exception.DataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcTemplate {
    private static final Logger log = LoggerFactory.getLogger(JdbcTemplate.class);

    public void update(final String query, final PreparedStatementSetter preparedStatementSetter) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatementSetter.setValue(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            throw new DataAccessException(exception);
        }
    }

    public void update(final String query, final Object... values) {
        update(query, createPreparedStatementSetter(values));
    }

    public <T> List<T> query(
            final String query,
            final RowMapper<T> rowMapper,
            final PreparedStatementSetter preparedStatementSetter) {

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatementSetter.setValue(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<T> results = new ArrayList<>();
            while (resultSet.next()) {
                results.add(rowMapper.mapRow(resultSet));
            }
            resultSet.close();
            return results;
        } catch (SQLException exception) {
            throw new DataAccessException(exception);
        }
    }

    public <T> List<T> query(final String query, final RowMapper<T> rowMapper, Object... values) {
        log.debug("valuesNullCheck={}", values == null);
        return query(query, rowMapper, createPreparedStatementSetter(values));
    }

    public <T> T queryForObject(
            final String query,
            final RowMapper<T> rowMapper,
            final PreparedStatementSetter preparedStatementSetter) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatementSetter.setValue(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            T result = null;
            if (resultSet.next()) {
                result = rowMapper.mapRow(resultSet);
            }
            resultSet.close();
            return result;
        } catch (SQLException exception) {
            throw new DataAccessException(exception);
        }
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
