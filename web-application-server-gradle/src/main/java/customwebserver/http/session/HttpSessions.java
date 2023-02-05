package customwebserver.http.session;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpSessions {
    public static final String SESSION_ID_NAME = "MYJESSIONID";
    private static final Logger log = LoggerFactory.getLogger(HttpSessions.class);

    private static final Map<String, HttpSession> sessionStore = new HashMap<>();

    private HttpSessions() {}

    public static HttpSession getSession(final String sessionId) {
        log.debug("input session id={}", sessionId);
        HttpSession httpSession = sessionStore.get(sessionId);
        if (httpSession == null) {
            httpSession = new HttpSession(sessionId);
            log.debug("create new session={}", httpSession);
            sessionStore.put(sessionId, httpSession);
        }
        return httpSession;
    }

    public static void remove(final String sessionId) {
        sessionStore.remove(sessionId);
    }
}
