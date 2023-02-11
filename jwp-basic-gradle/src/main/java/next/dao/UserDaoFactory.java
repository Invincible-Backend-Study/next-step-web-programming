package next.dao;

import next.dao.template.JdbcTemplate;

public class UserDaoFactory {

    private static final UserDao userDao = new UserDao( new JdbcTemplate());

    public static UserDao getUserDao(){
        return userDao;
    }

}
