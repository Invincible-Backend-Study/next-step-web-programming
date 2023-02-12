package next.dao;

import core.jdbc.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.JdbcTemplete;
import core.jdbc.PreparedStatementSetter;
import core.jdbc.RawMapper;
import next.model.User;

public class UserDao {
    public void addUser(User user) throws SQLException {
        PreparedStatementSetter pss = pstmt -> {
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
        };
        JdbcTemplete templete = new JdbcTemplete() {
        };
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        templete.insert(sql, pss);
    }


    public void updateUser(User newUser, String userId) throws SQLException {
        PreparedStatementSetter pss = pstmt -> {
            pstmt.setString(1, newUser.getUserId());
            pstmt.setString(2, newUser.getPassword());
            pstmt.setString(3, newUser.getName());
            pstmt.setString(4, newUser.getEmail());
            pstmt.setString(5, userId);
        };
        JdbcTemplete templete = new JdbcTemplete();
        String sql = "UPDATE USERS SET userId = ?, password = ?, name = ?, email = ? WHERE USERID = ?";
        templete.insert(sql, pss);
    }

    public User findByUserId(String userId) throws SQLException {
        RawMapper<User> rm = rs -> {
            if (rs.next()) {
                return new User(
                        rs.getString("userId"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email"));
            }
            return null;
        };
        JdbcTemplete templete = new JdbcTemplete();
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
        return templete.find(sql, rm);
    }


    public List<User> findAllUser() throws SQLException {
        RawMapper<List<User>> rm = rs -> {
            List<User> userList = new ArrayList<>();
            while (rs.next()) {
                User user = new User(
                        rs.getString("userId"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email"));
                userList.add(user);
            }
            return userList;
        };
        JdbcTemplete templete = new JdbcTemplete();
        String sql = "SELECT userId, name, password, email FROM USERS";
        return templete.find(sql, rm);
    }

}
