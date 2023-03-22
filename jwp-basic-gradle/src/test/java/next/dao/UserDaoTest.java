package next.dao;


import static org.assertj.core.api.Assertions.assertThat;

import core.jdbc.JdbcTemplate;
import java.util.List;
import next.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserDaoTest extends DaoTest {

    JdbcUserDao jdbcUserDao = new JdbcUserDao(new JdbcTemplate());

    @Test
    public void crud() {
        User expected = new User("userId", "password", "name", "javajigi@email.com");
        jdbcUserDao.insert(expected);

        User actual = jdbcUserDao.findById(expected.getUserId());
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void findAll() {
        // given
        User expected = new User("userId", "password", "name", "test@email.com");

        // when
        jdbcUserDao.insert(expected);
        List<User> users = jdbcUserDao.findAll();

        // then
        Assertions.assertAll(() -> {
            assertThat(users.size()).isEqualTo(2);
            assertThat(users).contains(expected);
        });
    }
}
