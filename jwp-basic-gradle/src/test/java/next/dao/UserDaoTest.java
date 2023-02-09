package next.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;


import next.model.User;
import org.junit.jupiter.api.Test;

public class UserDaoTest {

    @Test
    public void crud() throws Exception {
        User expected = new User("leejunho", "123", "leejunho", "javajigi@email.com");
        UserDao userDao = new UserDao();
        userDao.insert(expected);
        User actual = userDao.findByUserId(expected.getUserId());
        assertEquals(expected, actual);
    }

    @Test
    public void update_test() throws Exception {
        User user = new User("qwe", "123", "이준호", "javajigi@email.com");
        User expected = new User("qwe", "2", "123", "test@email.com");
        UserDao userDao = new UserDao();
        userDao.update(expected, user.getUserId());
        User actual = userDao.findByUserId(expected.getUserId());
        assertEquals(expected, actual);
    }

}
