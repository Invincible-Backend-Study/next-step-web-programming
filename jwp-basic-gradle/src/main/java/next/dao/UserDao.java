package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;
import core.jdbc.JdbcTemplate;
import core.jdbc.SelectJdbcTemplate;
import next.model.User;

public class UserDao {
    public static void insert(User user) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate() {  // 어나니머스 클래스
            @Override
            public void setParameters(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getUserId());
                pstmt.setString(2, user.getPassword());
                pstmt.setString(3, user.getName());
                pstmt.setString(4, user.getEmail());
            }
        };
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        jdbcTemplate.executeUpdate(sql);
    }

    public static List<User> findAll() throws SQLException {
        SelectJdbcTemplate selectJdbcTemplate = new SelectJdbcTemplate() {
            @Override
            public void setParameters(PreparedStatement pstmt) throws SQLException {
            }

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

        String sql = "SELECT userId, password, name, email FROM USERS";
        return (List<User>) selectJdbcTemplate.select(sql);
    }

    public static User findByUserId(String userId) throws SQLException {
        SelectJdbcTemplate selectJdbcTemplate = new SelectJdbcTemplate() {
            @Override
            public void setParameters(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, userId);
            }

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

        String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
        return (User) selectJdbcTemplate.select(sql);
    }

    public static int update(User user) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate() {
            @Override
            public void setParameters(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getPassword());
                pstmt.setString(2, user.getName());
                pstmt.setString(3, user.getEmail());
                pstmt.setString(4, user.getUserId());
            }
        };

        String sql = "UPDATE USERS SET password=?, name=?, email=? WHERE userid=?";
        return jdbcTemplate.executeUpdate(sql);
    }

    public static int deleteAll() throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate() {
            @Override
            public void setParameters(PreparedStatement pstmt) throws SQLException {
            }
        };

        String sql = "DELETE FROM USERS";
        return jdbcTemplate.executeUpdate(sql);
    }
}
