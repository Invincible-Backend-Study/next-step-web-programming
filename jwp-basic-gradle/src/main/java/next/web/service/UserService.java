package next.web.service;

import java.util.List;
import next.dao.UserDao;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserDao userDao = new UserDao();

    public void updateUserInformation(final User updatedUser) {
        userDao.update(updatedUser);
    }

    public User loginUser(final String userId, final String password) {
        if (userId.equals("") || password.equals("")) {
            throw new IllegalArgumentException("[ERROR] 로그인 정보를 입력해야 합니다.");
        }
        User findUser = userDao.findByUserId(userId);
        if (findUser == null) {
            throw new IllegalArgumentException("[ERROR] 일치하는 사용자 정보가 없습니다.");
        }
        if (findUser.containPassword(password)) {
            return findUser;
        }
        return null;
    }

    public User findUserById(final String userId) {
        return userDao.findByUserId(userId);
    }

    public List<User> findAllUsers() {
        return userDao.findAll();
    }

    public void signUp(final User user) {
        userDao.insert(user);
    }
}
