package next.api.auth.service;

import next.api.auth.controller.LoginController;
import next.api.user.dao.UserDao;
import next.api.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private static AuthService authService = new AuthService();
    private AuthService() {};
    public static AuthService getInstance() {
        return authService;
    }
    private final UserDao userDao = UserDao.getInstance();

    public User getUser(final String id, final String password) {
        User user;
        user = userDao.findByUserId(id);
        if (user == null) {
            return null;
        }
        if (user.isAuthWith(id, password)) {
            return user;
        }

        return null;
    }
}
