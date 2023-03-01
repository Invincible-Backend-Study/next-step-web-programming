package next.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import next.model.Answer;
import next.model.Question;
import next.model.User;
import org.apache.taglibs.standard.lang.jstl.test.beans.PublicBean1;
import org.junit.jupiter.api.Test;

public class UserDaoTest {

    @Test
    public void crud() throws Exception {
        User expected = new User("leejunho", "123", "leejunho", "javajigi@email.com");
        UserDao userDao = new UserDao();
        userDao.addUser(expected);
        User actual = userDao.findByUserId(expected.getUserId());
        assertEquals(expected, actual);
    }

    @Test
    public void update_test() throws Exception {
        User user = new User("test", "123", "이준호", "javajigi@email.com");
        User expected = new User("qwe", "2", "123", "test@email.com");
        UserDao userDao = new UserDao();
        userDao.addUser(user);
        userDao.updateUser(expected, user.getUserId());
        User actual = userDao.findByUserId(expected.getUserId());
        assertEquals(expected, actual);
    }

    @Test
    public void find_all_users() throws Exception {
        UserDao userDao = new UserDao();
        User user = new User("wnsgh12s", "123", "이준호", "junho1273@gmail.com");
        User admin = new User("admin", "password", "자바지기", "admin@slipp.net");
        userDao.addUser(user);
        List<User> findAlluser = userDao.findAllUser();
        List<User> expected = Arrays.asList(user, admin);
        assertTrue(findAlluser.containsAll(expected));
    }

    @Test
    public void test() {
        AnswerDao dao = new AnswerDao();
        Answer as = new Answer("이준호","이건데",8);
    }


}
