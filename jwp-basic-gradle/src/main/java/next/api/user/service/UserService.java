package next.api.user.service;

import next.api.user.dao.UserDao;
import next.api.user.model.User;

import java.util.List;

public class UserService {
    private static UserService userService = new UserService();

    private UserService() {}

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

    public User getUserByUserId(String userId) {
        return userDao.findByUserId(userId);
    }

    public User getUserByName(String name) {
        return userDao.findByName(name);
    }

    public List<User> getUsers() {
        List<User> users;
        users = userDao.findAll();
        return users;
    }
}
