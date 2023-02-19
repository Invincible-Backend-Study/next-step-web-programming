package core.jdbc;

import static core.jdbc.ConnectionManager.getConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {

    public void update(String sql, PreparedStatementSetter preparedStatementSetter) throws DataAccessException {

        try (final var connection = ConnectionManager.getConnection(); final var preparedStatement = connection.prepareStatement(sql)){
            preparedStatementSetter.setValues(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
    public void update(PreparedStatementCreator psc, KeyHolder holder) {
        try (final var conn = ConnectionManager.getConnection()) {
            PreparedStatement ps = psc.createPreparedStatement(conn);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            // key를 만들어내지 못하는 경우 빈 객체를 리턴해?
            if (rs.next()) {
                // the column value; if the value is SQL NULL, the value returned is 0
                // null이면 0인점이 조금 이상함
                holder.setId(rs.getLong(1));
            }
            rs.close();
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
    public <T> List<T> query(String sql, PreparedStatementSetter preparedStatementSetter, RowMapper<T> rowMapper) throws DataAccessException {
        try(final var connection = getConnection();
            final var preparedStatement = connection.prepareStatement(sql);){

            preparedStatementSetter.setValues(preparedStatement);
            return resultSetToMapper(rowMapper, preparedStatement);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public <T> T queryForObject(String sql, PreparedStatementSetter preparedStatementSetter, RowMapper<T> rowMapper) throws DataAccessException {
        final var result = this.query(sql, preparedStatementSetter,rowMapper);
        return result.isEmpty() ? null : result.get(0);
    }
}
