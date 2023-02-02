package next.dao;


import next.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserDaoTest {

    @Test
    public void crud() throws Exception {
        User expected = new User("userId", "password", "name", "javajigi@email.com");
        UserDao userDao = new UserDao();
        userDao.insert(expected);

        User actual = userDao.findByUserId(expected.getUserId());
        Assertions.assertThat(actual).isEqualTo(expected);
    }

}
