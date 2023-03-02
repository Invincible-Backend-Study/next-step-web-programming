package next.utils;

import javax.servlet.http.HttpSession;

public class SessionUtil {

    public static boolean isLogined(final HttpSession session, final String objectName) {
        return session.getAttribute(objectName) != null;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getLoginObject(final HttpSession session, final String objectName) {
        return (T) session.getAttribute(objectName);
    }

}
