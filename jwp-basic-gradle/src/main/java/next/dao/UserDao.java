package next.dao;

import core.jdbc.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.JdbcTemplete;
import next.model.User;

public class UserDao {
    public void addUser(User user) throws SQLException {
        JdbcTemplete templete = new JdbcTemplete() {
            @Override
            public void setParameters(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getUserId());
                pstmt.setString(2, user.getPassword());
                pstmt.setString(3, user.getName());
                pstmt.setString(4, user.getEmail());
            }
        };
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        templete.insert(sql);
    }


    public void updateUser(User newUser, String userId) throws SQLException {
        JdbcTemplete templete = new JdbcTemplete() {
            @Override
            public void setParameters(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, newUser.getUserId());
                pstmt.setString(2, newUser.getPassword());
                pstmt.setString(3, newUser.getName());
                pstmt.setString(4, newUser.getEmail());
                pstmt.setString(5, userId);
            }
        };
        String sql = "UPDATE USERS SET userId = ?, password = ?, name = ?, email = ? WHERE USERID = ?";
        templete.insert(sql);
    }

    public User findByUserId(String userId) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();

            User user = null;
            if (rs.next()) {
                user = new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                        rs.getString("email"));
            }

            return user;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }


    public List<User> findAll() throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            String sql = "SELECT userId, name, password, email FROM USERS";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
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
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
}
