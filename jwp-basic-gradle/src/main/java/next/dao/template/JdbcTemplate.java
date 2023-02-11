package next.dao.template;

import core.jdbc.ConnectionManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {

    public void update(String sql, PreparedStatementSetter preparedStatementSetter) throws DataAccessException {

        try(final var connection = ConnectionManager.getConnection(); final var preparedStatement = connection.prepareStatement(sql)){
            preparedStatementSetter.setValues(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    private <T> List<T> resultSetToMapper(RowMapper<T> rowMapper, PreparedStatement preparedStatement) throws SQLException {
        try(final var resultSet = preparedStatement.executeQuery()){
            final var result = new ArrayList<T>();

            while(resultSet.next()){
                result.add(rowMapper.mapRow(resultSet));
            }
            return result;
        }
    }
    public <T> List<T> query(String sql, PreparedStatementSetter preparedStatementSetter, RowMapper<T> rowMapper) throws DataAccessException{
        try(final var connection = ConnectionManager.getConnection();
            final var preparedStatement = connection.prepareStatement(sql);){

            preparedStatementSetter.setValues(preparedStatement);
            return resultSetToMapper(rowMapper, preparedStatement);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public <T> T queryForObject(String sql, PreparedStatementSetter preparedStatementSetter, RowMapper<T> rowMapper) throws DataAccessException{
        final var result = this.query(sql, preparedStatementSetter,rowMapper);
        return result.isEmpty() ? null : result.get(0);
    }
}
