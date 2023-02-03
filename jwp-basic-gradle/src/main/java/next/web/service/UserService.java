package next.web.service;

import core.db.DataBase;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public void updateUserInformation(final User updatedUser) {
        DataBase.deleteUserById(updatedUser.getUserId());
        DataBase.addUser(updatedUser);
    }

    public User loginUser(final String userId, final String password) {
        if (userId.equals("") || password.equals("")) {
            throw new IllegalArgumentException("[ERROR] 로그인 정보를 입력해야 합니다.");
        }
        User findUser = DataBase.findUserById(userId);
        if (findUser == null) {
            throw new IllegalArgumentException("[ERROR] 일치하는 사용자 정보가 없습니다.");
        }
        if (findUser.containPassword(password)) {
            return findUser;
        }
        return null;
    }
}
