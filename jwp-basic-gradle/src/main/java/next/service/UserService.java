package next.service;

import core.annotation.Inject;
import java.util.List;
import next.dao.JdbcUserDao;
import next.model.User;

public class UserService {

    private final JdbcUserDao userDao;

    @Inject
    public UserService(final JdbcUserDao userDao) {
        this.userDao = userDao;
    }

    public void updateUserInformation(final User updatedUser) {
        userDao.update(updatedUser);
    }

    public User loginUser(final String userId, final String password) {
        if (userId.equals("") || password.equals("")) {
            throw new IllegalArgumentException("[ERROR] 로그인 정보를 입력해야 합니다.");
        }
        User findUser = userDao.findById(userId);
        if (findUser == null) {
            throw new IllegalArgumentException("[ERROR] 일치하는 사용자 정보가 없습니다.");
        }
        if (findUser.containPassword(password)) {
            return findUser;
        }
        return null;
    }

    public List<User> findAllUsers() {
        return userDao.findAll();
    }

    public void signUp(final User user) {
        userDao.insert(user);
    }

}
