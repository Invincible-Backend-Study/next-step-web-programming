package webserver.http;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MyHttpSession {
    private static final String SESSION_ID = "JSESSIONID";
    private Map<String, Object> values = new HashMap<>();

    public MyHttpSession() {
        values.put(SESSION_ID, UUID.randomUUID());
    }

    public MyHttpSession(String uuid) {
        values.put(SESSION_ID, uuid);
    }

    public String getId() {
        return String.valueOf(values.get(SESSION_ID));
    }

    public void setAttribute(String name, Object value) {
        values.put(name, value);
    }

    public Object getAttribute(String name) {
        return values.get(name);
    }

    public void removeAttribute(String name) {
        values.remove(name);
    }

    public void invalidate() {
        values.clear();
    }

    @Override
    public String toString() {
        return ((values == null) ? "-" : values.toString());
    }
}
