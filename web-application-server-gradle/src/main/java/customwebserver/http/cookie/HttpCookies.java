package customwebserver.http.cookie;

import java.util.HashMap;
import java.util.Map;

public class HttpCookies {
    private final Map<String, Object> httpCookies = new HashMap<>();

    public HttpCookies() {
    }

    public String createCookieMessage() {
        StringBuilder cookieMessage = new StringBuilder();
        cookieMessage.append("Set-Cookie: ");
        if (!httpCookies.isEmpty()) {
            httpCookies.keySet()
                    .forEach(key -> cookieMessage.append(key)
                            .append("=")
                            .append(httpCookies.get(key))
                            .append("; "));
            return cookieMessage.append(" path=/")
                    .append("\r\n")
                    .toString();
        }
        return "";
    }

    public void addCookie(final String cookieKey, final Object value) {
        httpCookies.put(cookieKey, value);
    }
}
