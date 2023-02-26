package next.common.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import next.user.entity.User;

public class UserUtils {

    public static boolean isLoggedIn(HttpSession session) {
        var user = session.getAttribute("user");
        return user != null;
    }

    public static User getUserBy(HttpServletRequest request) {
        var user = request.getSession().getAttribute("user");
        return (User) user;
    }
}
