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
            public Object mapRow(ResultSet rs) throws SQLException {
                return null;
            }

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
            public Object mapRow(ResultSet rs) throws SQLException {
                return null;
            }

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
        JdbcTemplete templete = new JdbcTemplete() {
            @Override
            public Object mapRow(ResultSet rs) throws SQLException {
                if (rs.next()) {
                    return new User(
                            rs.getString("userId"),
                            rs.getString("password"),
                            rs.getString("name"),
                            rs.getString("email"));
                }
                return null;
            }

            @Override
            public void setParameters(PreparedStatement pstmt) throws SQLException {
            }

            ;

        };
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
        return (User) templete.find(sql);
    }


    public List<User> findAllUser() throws SQLException {
        JdbcTemplete templete = new JdbcTemplete() {
            @Override
            public Object mapRow(ResultSet rs) throws SQLException {
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
            }

            @Override
            public void setParameters(PreparedStatement pstmt) throws SQLException {
                return;
            }
        };
        String sql = "SELECT userId, name, password, email FROM USERS";
        return (List<User>) templete.find(sql);
    }

}
