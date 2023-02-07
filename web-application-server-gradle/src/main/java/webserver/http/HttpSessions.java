package webserver.http;

import java.util.HashMap;
import java.util.Map;

public class HttpSessions {
    private static Map<String, Object> sessions = new HashMap<>();  // JSESSIONID = (MyHttpSession)

    public static void add(String uuid, MyHttpSession httpSession) {
        sessions.put(uuid, httpSession);
    }

    public static MyHttpSession get(String uuid) {
        Object session = sessions.get(uuid);
        if (session == null) {
            sessions.put(uuid, new MyHttpSession(uuid));
        }
        return (MyHttpSession) sessions.get(uuid);
    }

    public static void remove(String uuid) {
        sessions.remove(uuid);
    }
}
