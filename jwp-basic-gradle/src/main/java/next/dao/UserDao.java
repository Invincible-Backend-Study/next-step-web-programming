package next.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.*;
import next.model.User;

public class UserDao {
    static final JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public static void insert(User user) throws SQLException {
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        jdbcTemplate.executeUpdate(sql, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
    }

    public static List<User> findAll() throws SQLException {
        ResultSetMapper<List<User>> resultSetMapper = rs -> {
            List<User> users = new ArrayList<User>();
            while (rs.next()) {
                users.add(new User(
                        rs.getString("userId"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email")));
            }
            return users;
        };

        String sql = "SELECT userId, password, name, email FROM USERS";
        return jdbcTemplate.select(sql, resultSetMapper);
    }

    public static User findByUserId(String userId) throws SQLException {
        ResultSetMapper<User> resultSetMapper = rs -> {
            if (rs.next()) {
                return new User(
                        rs.getString("userId"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email")
                );
            }
            return null;
        };

        String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
        return jdbcTemplate.select(sql, resultSetMapper, userId);
    }

    public static int update(User user) throws SQLException {
        String sql = "UPDATE USERS SET password=?, name=?, email=? WHERE userid=?";
        return jdbcTemplate.executeUpdate(sql, user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
    }

    public static int deleteAll() throws SQLException {
        String sql = "DELETE FROM USERS";
        return jdbcTemplate.executeUpdate(sql);
    }
}
