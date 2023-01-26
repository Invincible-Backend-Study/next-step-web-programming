package webserver.http;

import util.HttpRequestUtils;

import java.util.Collections;
import java.util.Map;

public class RequestLine {
    private final String method;
    private final String path;
    private final Map<String, String> params;

    public RequestLine(String line) {
        // RequestLine 가져오기 (Method, RequestPath&Parameter, HttpVer)
        String[] requestLine = line.split(" ");
        method = requestLine[0];
        String url = requestLine[1];

        int index = url.indexOf("?");
        if (index != -1) {
            path = url.substring(0, index);
            params = HttpRequestUtils.parseQueryString(url.substring(index + 1));
        } else {
            path = url;
            params = Collections.emptyMap();
        }
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getParams() {
        return params;
    }

    @Override
    public String toString() {
        return "method:" + method + ", path:" + path + ", params:" + params.toString();
    }
}
