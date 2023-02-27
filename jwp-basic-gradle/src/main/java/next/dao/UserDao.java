package next.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.JdbcTemplete;
import core.jdbc.RawMapper;
import next.model.User;

public class UserDao {

    private final JdbcTemplete templete = new JdbcTemplete();

    //INSERT
    public void addUser(User user) {
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        templete.excuteSqlUpdate(sql, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
    }

    public void removeUser(String userId) {
        String sql = "DELETE FROM USERS WHERE USERID = ?";
        templete.excuteSqlUpdate(sql, userId);
    }

    //UPDATE
    public void updateUser(User newUser, String userId) {
        String sql = "UPDATE USERS SET userId = ?, password = ?, name = ?, email = ? WHERE USERID = ?";
        templete.excuteSqlUpdate(sql, newUser.getUserId(), newUser.getPassword(), newUser.getName(), newUser.getEmail(),
                userId);
    }

    //SELECT
    public User findByUserId(String userId) {
        RawMapper<List<User>> rm = findedUserResult();
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
        if (userId == null) {
            return null;
        }
        return templete.excuteFindDynamicData(sql, rm, userId).get(0);
    }

    public List<User> findAllUser() {
        RawMapper<List<User>> rm = findedUserResult();
        String sql = "SELECT userId, name, password, email FROM USERS";
        return templete.excuteFindStaticData(sql, rm);
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
