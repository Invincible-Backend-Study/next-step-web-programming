package next.user.dao;


public class UserDaoFactory {

    private static final UserDao userDao = UserDao.getInstance();

    public static UserDao getUserDao() {
        return userDao;
    }

}
