package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.*;
import next.model.User;

public class UserDao {
    public static void insert(User user) throws SQLException {
        PreparedStatementParameters ps = new PreparedStatementParameters() {
            @Override
            public void setParameters(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getUserId());
                pstmt.setString(2, user.getPassword());
                pstmt.setString(3, user.getName());
                pstmt.setString(4, user.getEmail());
            }
        };

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        jdbcTemplate.executeUpdate(sql, ps);
    }

    public static List<User> findAll() throws SQLException {
        PreparedStatementParameters ps = new PreparedStatementParameters() {
            @Override
            public void setParameters(PreparedStatement pstmt) throws SQLException {
            }
        };
        ResultSetMapper resultSetMapper = new ResultSetMapper() {
            @Override
            public Object mapRow(ResultSet rs) throws SQLException {
                List<User> users = new ArrayList<User>();
                while (rs.next()) {
                    users.add(new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                            rs.getString("email")));
                }
                return users;
            }
        };

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT userId, password, name, email FROM USERS";
        return (List<User>) jdbcTemplate.select(sql, ps, resultSetMapper);
    }

    public static User findByUserId(String userId) throws SQLException {
        PreparedStatementParameters ps = new PreparedStatementParameters() {
            @Override
            public void setParameters(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, userId);
            }
        };
        ResultSetMapper resultSetMapper = new ResultSetMapper() {
            @Override
            public Object mapRow(ResultSet rs) throws SQLException {
                User user = null;
                if (rs.next()) {
                    user = new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                            rs.getString("email"));
                }
                return user;
            }
        };

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
        return (User) jdbcTemplate.select(sql, ps, resultSetMapper);
    }

    public static int update(User user) throws SQLException {
        PreparedStatementParameters ps = new PreparedStatementParameters() {
            @Override
            public void setParameters(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getPassword());
                pstmt.setString(2, user.getName());
                pstmt.setString(3, user.getEmail());
                pstmt.setString(4, user.getUserId());
            }
        };

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "UPDATE USERS SET password=?, name=?, email=? WHERE userid=?";
        return jdbcTemplate.executeUpdate(sql, ps);
    }

    public static int deleteAll() throws SQLException {
        PreparedStatementParameters ps = new PreparedStatementParameters() {
            @Override
            public void setParameters(PreparedStatement pstmt) throws SQLException {
            }
        };

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "DELETE FROM USERS";
        return jdbcTemplate.executeUpdate(sql, ps);
    }
}
