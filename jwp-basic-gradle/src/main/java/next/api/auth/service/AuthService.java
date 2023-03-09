package next.api.auth.service;

import core.annotation.Inject;
import core.annotation.Service;
import next.api.user.dao.UserDao;
import next.api.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final UserDao userDao;

    @Inject
    private AuthService(UserDao userDao) {
        this.userDao = userDao;
    }

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
