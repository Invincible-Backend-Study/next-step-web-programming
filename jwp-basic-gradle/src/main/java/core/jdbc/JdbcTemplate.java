package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import next.exception.DataAccessException;

public class JdbcTemplate {
    public void update(final String query, final PreparedStatementSetter preparedStatementSetter) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatementSetter.setValue(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            throw new DataAccessException(exception.getMessage());
        }
    }

    public void update(final String query, final Object... values) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            PreparedStatementSetter preparedStatementSetter = createPreparedStatementSetter(values);
            preparedStatementSetter.setValue(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            throw new DataAccessException(exception.getMessage());
        }
    }


    public <T> List<T> query(final String query, final RowMapper<T> rowMapper) {
        try (Connection connection = ConnectionManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            List<T> results = new ArrayList<>();
            while (resultSet.next()) {
                results.add(rowMapper.mapRow(resultSet));
            }
            return results;
        } catch (SQLException exception) {
            throw new DataAccessException(exception.getMessage());
        }
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
            throw new DataAccessException(exception.getMessage());
        }
    }

    public <T> T queryForObject(
            final String query,
            final RowMapper<T> rowMapper,
            final Object... values) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            PreparedStatementSetter preparedStatementSetter = createPreparedStatementSetter(values);
            preparedStatementSetter.setValue(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            T result = null;
            if (resultSet.next()) {
                result = rowMapper.mapRow(resultSet);
            }
            resultSet.close();
            return result;
        } catch (SQLException exception) {
            throw new DataAccessException(exception.getMessage());
        }
    }

    private PreparedStatementSetter createPreparedStatementSetter(final Object[] values) {
        return preparedStatement -> {
            for (int count = 1; count <= values.length; count++) {
                preparedStatement.setObject(count, values[count - 1]);
            }
        };
    }
}
