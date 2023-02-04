package customwebserver.http.session;

import java.util.HashMap;
import java.util.Map;

public class HttpSessions {
    private static final Map<String, HttpSession> sessionStore = new HashMap<>();

    private HttpSessions() {}

    public static HttpSession getSession(final String sessionId) {
        HttpSession httpSession = sessionStore.get(sessionId);
        if (httpSession == null) {
            httpSession = new HttpSession(sessionId);
            sessionStore.put(sessionId, new HttpSession(sessionId));
        }
        return httpSession;
    }
}
