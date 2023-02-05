package next.utils;

import javax.servlet.http.HttpSession;

public class UserSessionUtils {
    public static boolean isLoggedIn(HttpSession session) {
        var user = session.getAttribute("user");
        return user != null;
    }
}
