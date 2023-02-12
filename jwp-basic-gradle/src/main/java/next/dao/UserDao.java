package next.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.JdbcTemplete;
import core.jdbc.RawMapper;
import next.model.User;

public class UserDao {

    private final JdbcTemplete templete = new JdbcTemplete();

    public void addUser(User user) throws SQLException {
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        templete.insert(sql, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
    }


    public void updateUser(User newUser, String userId) throws SQLException {
        String sql = "UPDATE USERS SET userId = ?, password = ?, name = ?, email = ? WHERE USERID = ?";
        templete.insert(sql, newUser.getUserId(), newUser.getPassword(), newUser.getName(), newUser.getEmail(), userId);
    }

    public User findByUserId(String userId) throws SQLException {
        RawMapper<List<User>> rm = findedUserResult();
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
        return templete.find(sql, rm, userId).get(0);
    }

    public List<User> findAllUser() throws SQLException {
        RawMapper<List<User>> rm = findedUserResult();
        String sql = "SELECT userId, name, password, email FROM USERS";
        return templete.find(sql, rm);
    }

    private RawMapper<List<User>> findedUserResult() {
        return rs -> {
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
    }


}
