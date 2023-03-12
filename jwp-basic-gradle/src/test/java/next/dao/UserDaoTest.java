package next.dao;


import static org.assertj.core.api.Assertions.assertThat;

import core.jdbc.ConnectionManager;
import java.util.List;
import next.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

public class UserDaoTest extends DaoTest {


    @Test
    public void crud() {
        User expected = new User("userId", "password", "name", "javajigi@email.com");
        UserDao userDao = new UserDao();
        userDao.insert(expected);

        User actual = userDao.findById(expected.getUserId());
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void findAll() {
        // given
        User expected = new User("userId", "password", "name", "test@email.com");
        UserDao userDao = new UserDao();

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
