package next.user.dao;


public class UserDaoFactory {

    private static final UserDao userDao = new UserDao();

    public static UserDao getUserDao() {
        return userDao;
    }

}
