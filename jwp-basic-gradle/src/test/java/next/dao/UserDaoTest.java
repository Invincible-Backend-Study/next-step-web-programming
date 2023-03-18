package next.dao;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import next.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserDaoTest extends DaoTest {


    @Test
    public void crud() {
        User expected = new User("userId", "password", "name", "javajigi@email.com");
        JdbcUserDao userDao = new JdbcUserDao();
        userDao.insert(expected);

        User actual = userDao.findById(expected.getUserId());
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void findAll() {
        // given
        User expected = new User("userId", "password", "name", "test@email.com");
        JdbcUserDao userDao = new JdbcUserDao();

        // when
        userDao.insert(expected);
        List<User> users = userDao.findAll();

        // then
        Assertions.assertAll(() -> {
            assertThat(users.size()).isEqualTo(2);
            assertThat(users).contains(expected);
        });
    }
}
