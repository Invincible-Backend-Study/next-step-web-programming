package customwebserver.http.session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 구현 메소드 목록 <br> String getId(): get current session id <br> void setAttribute(String name, Object value): set
 * attribute in session with key value <br> Object getAttribute(String name): get session attribute with key <br> void
 * removeAttribute(String name): remove session attribute with key <br> void invalidate(): expired all session
 * attribute
 */
public class HttpSession {
    private final Map<String, Object> store = new HashMap<>();
    private String sessionId;

    public HttpSession(final String sessionId) {
        this.sessionId = sessionId;
    }

    public String getId() {
        return this.sessionId;
    }

    public void setAttribute(final String name, final Object value) {
        store.put(name, value);
    }

    public Object getAttribute(final String name) {
        return store.get(name);
    }

    public void removeAttribute(final String name) {
        store.remove(name);
    }

    public void invalidate() {
        HttpSessions.remove(sessionId);
    }

    @Override
    public String toString() {
        return "HttpSession{" +
                "store=" + store +
                ", sessionId='" + sessionId + '\'' +
                '}';
    }
}
