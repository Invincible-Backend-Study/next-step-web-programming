package next.dao;


import core.jdbc.JdbcTemplate;

public class UserDaoFactory {

    private static final UserDao userDao = new UserDao(new JdbcTemplate());

    public static UserDao getUserDao(){
        return userDao;
    }

}
