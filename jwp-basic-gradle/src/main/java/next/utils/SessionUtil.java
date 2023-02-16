package next.utils;

import javax.servlet.http.HttpSession;

public class SessionUtil {

    public static boolean isLogin(final HttpSession session, final String objectName) {
        return session.getAttribute(objectName) != null;
    }
}
