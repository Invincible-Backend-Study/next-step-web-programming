package next.user.service;

import com.jwp.outbound.user.infrastructure.UserDao;
import core.annotation.Service;
import java.util.List;
import next.user.entity.User;


@Service
public class FindUserService {

    private final UserDao userDao = UserDao.getInstance();

    public static FindUserService getInstance() {
        return FindUserServiceHolder.FIND_USER_SERVICE;
    }

    public List<User> findAll() {
        return null;
        //return userDao.findAll();
    }

    private static class FindUserServiceHolder {
        private static final FindUserService FIND_USER_SERVICE = new FindUserService();
    }
}
