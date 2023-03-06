package next.service;

import java.util.List;
import next.dao.UserDao;
import next.model.User;

public class UserService {

    private final UserDao userDao = new UserDao();
    public void addUser(User user) {
        userDao.addUser(user);
    }

    public User findByUserId(String userId) {
        return userDao.findByUserId(userId);
    }

    public void updateUser(User newUser, String userId) {
        userDao.updateUser(newUser,userId);
    }

    public List<User> findAllUser() {
        return userDao.findAllUser();
    }
}
