package next.dao;

import core.jdbc.JdbcTemplate;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import next.model.User;

public class UserDao {
    public void insert(User user) {
        JdbcTemplate insertTemplate = new JdbcTemplate() {
            @Override
            protected void setValue(final PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getUserId());
                pstmt.setString(2, user.getPassword());
                pstmt.setString(3, user.getName());
                pstmt.setString(4, user.getEmail());
            }

            @Override
            protected Object mapRow(final ResultSet resultSet) throws SQLException {
                return null;
            }
        };
        insertTemplate.update("INSERT INTO USERS VALUES (?, ?, ?, ?)");
    }

    public void update(final User user) {
        JdbcTemplate updateTemplate = new JdbcTemplate() {
            @Override
            protected void setValue(final PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getPassword());
                pstmt.setString(2, user.getName());
                pstmt.setString(3, user.getEmail());
                pstmt.setString(4, user.getUserId());
            }

            @Override
            protected Object mapRow(final ResultSet resultSet) throws SQLException {
                return null;
            }
        };
        updateTemplate.update("UPDATE USERS SET password = ?, name = ?, email = ? WHERE userId = ?");
    }

    public User findByUserId(String userId) {
        JdbcTemplate selectTemplate = new JdbcTemplate() {
            @Override
            protected void setValue(final PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, userId);
            }

            @Override
            protected Object mapRow(final ResultSet resultSet) throws SQLException {
                return new User(
                        resultSet.getString("userId"),
                        resultSet.getString("password"),
                        resultSet.getString("name"),
                        resultSet.getString("email")
                );
            }
        };
        return (User) selectTemplate.queryForObject("SELECT userId, password, name, email FROM USERS WHERE userId = ?");
    }

    public List<User> findAll() {
        JdbcTemplate selectTemplate = new JdbcTemplate() {
            @Override
            protected void setValue(final PreparedStatement preparedStatement) throws SQLException {
            }

            @Override
            protected Object mapRow(final ResultSet resultSet) throws SQLException {
                return new User(
                        resultSet.getString("userId"),
                        resultSet.getString("password"),
                        resultSet.getString("name"),
                        resultSet.getString("email")
                );
            }

        };
        return selectTemplate.query("SELECT userId, password, name, email from USERS");
    }
}
