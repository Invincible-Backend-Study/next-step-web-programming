package next.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    User user;

    @BeforeEach
    void beforeEach() {
        user = new User("myid", "mypassword", "myname", "my@email.com");
    }

    @Test
    void isAuthWith_정상() {
        boolean canLogin = user.isAuthWith("myid", "mypassword");
        assertTrue(canLogin);
    }

    @Test
    void isAuthWith_불가능() {
        boolean canLogin = user.isAuthWith("notmyid", "notmypassword");
        assertFalse(canLogin);
    }
}