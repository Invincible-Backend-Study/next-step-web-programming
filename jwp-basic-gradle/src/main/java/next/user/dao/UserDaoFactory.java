package next.user.dao;


import com.jwp.outbound.user.infrastructure.UserDao;

public class UserDaoFactory {

    private static final UserDao userDao = UserDao.getInstance();

    public static UserDao getUserDao() {
        return userDao;
    }

}
