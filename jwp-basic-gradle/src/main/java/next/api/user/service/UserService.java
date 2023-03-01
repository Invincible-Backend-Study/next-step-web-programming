package next.api.user.service;

import next.api.user.dao.UserDao;
import next.api.user.model.User;

import java.sql.SQLException;
import java.util.List;

public class UserService {
    private static UserService userService = new UserService();
    private UserService() {};
    public static UserService getInstance() {
        return userService;
    }
    private final UserDao userDao = UserDao.getInstance();

    public void addUser(User user) {
        userDao.insert(user);
    }

    public void updateUser(User user) {
        userDao.update(user);
    }

    public List<User> getUsers() {
        List<User> users;
        users = userDao.findAll();
        return users;
    }
}
