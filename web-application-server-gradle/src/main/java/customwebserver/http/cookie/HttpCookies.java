package customwebserver.http.cookie;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpCookies {
    private static final Logger log = LoggerFactory.getLogger(HttpCookies.class);
    private final Map<String, String> httpCookies = new HashMap<>();

    public HttpCookies() {
    }

    public HttpCookies(final Map<String, String> httpCookies) {
        this.httpCookies.putAll(httpCookies);
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

    public void addCookie(final String cookieKey, final String value) {
        httpCookies.put(cookieKey, value);
    }

    public String getCookie(final String cookieKey) {
        return httpCookies.get(cookieKey);
    }
}
