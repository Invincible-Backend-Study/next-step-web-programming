package next.api.user.service;

import next.api.user.dao.UserDao;
import next.api.user.model.User;

import java.util.List;

public class UserService {
    private final UserDao userDao;
    private UserService(UserDao userDao) {
        this.userDao = userDao;
    }

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
