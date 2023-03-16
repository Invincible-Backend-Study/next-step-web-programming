package next.api.user.dao;

import core.annotation.Repository;
import core.jdbc.JdbcTemplate;
import core.jdbc.ResultSetMapper;
import next.api.user.model.User;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDao {
    static final JdbcTemplate jdbcTemplate = JdbcTemplate.getInstance();
//    private static UserDao userDao = new UserDao();
//    private UserDao() {}
//    public static UserDao getInstance() {
//        return userDao;
//    }

    public void insert(User user) {
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        jdbcTemplate.executeUpdate(sql, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
    }

    public List<User> findAll() {
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

    public User findByUserId(String userId) {
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

    public User findByName(String name) {
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

        String sql = "SELECT userId, password, name, email FROM USERS WHERE name=?";
        return jdbcTemplate.select(sql, resultSetMapper, name);
    }

    public int update(User user) {
        String sql = "UPDATE USERS SET password=?, name=?, email=? WHERE userid=?";
        return jdbcTemplate.executeUpdate(sql, user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
    }

    public int deleteAll() {
        String sql = "DELETE FROM USERS";
        return jdbcTemplate.executeUpdate(sql);
    }
}
