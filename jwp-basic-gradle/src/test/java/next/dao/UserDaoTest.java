package next.dao;


import com.jwp.outbound.user.infrastructure.UserDao;
import next.dao.template.DataTest;
import next.user.dao.UserDaoFactory;
import org.junit.jupiter.api.Test;

public class UserDaoTest extends DataTest {
    private final UserDao userDao = UserDaoFactory.getUserDao();

    @Test
    public void crud() throws Exception {
     /*   User expected = new User("userId", "password", "name", "javajigi@email.com");
        userDao.insert(expected);
        User actual = userDao.findByUserId(expected.getUserId());
        assertEquals(expected, actual);

        expected.updateUserInformation("password2", "name2", "sanjigi@email.com");
        userDao.update(expected);
        actual = userDao.findByUserId(expected.getUserId());
        assertEquals(expected, actual);*/
    }

    @Test
    public void findAll() throws Exception {
//        List<User> users = userDao.findAll();
        //assertEquals(1, users.size());
    }
}
