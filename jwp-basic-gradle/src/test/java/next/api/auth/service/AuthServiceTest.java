package next.api.auth.service;

import next.api.user.dao.UserDao;
import next.api.user.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock
    private UserDao userDao;

    @InjectMocks
    private AuthService authService;

    @Test
    void getUser_유저존재() {
        User user = new User("id", "pw", "김이름", "aaa@naver.com");

        when(userDao.findByUserId("id")).thenReturn(user);

        User userResult = authService.getUser("id", "pw");
        assertAll(() -> {
            assertEquals(user.getUserId(), userResult.getUserId());
            assertEquals(user.getPassword(), userResult.getPassword());
            assertEquals(user.getName(), userResult.getName());
            assertEquals(user.getEmail(), userResult.getEmail());
        });
    }

    @Test
    void getUser_유저없음() {
        when(userDao.findByUserId("id")).thenReturn(null);

        User userResult = authService.getUser("id", "pw");
        assertEquals(null, userResult);
    }
}
