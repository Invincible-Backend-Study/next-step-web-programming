package customwebserver.http;

import java.util.Map;
import utils.HttpRequestUtils;
import utils.enums.HttpMethod;

public class RequestLine {
    public static final int QUERY_STRING = 1;
    public static final int REQUEST_URI = 0;

    private final HttpMethod httpMethod;
    private String requestUri;
    private Map<String, String> params;
    private final String httpVersion;

    private RequestLine(final HttpMethod httpMethod, final String requestUri, final String httpVersion) {
        this.httpMethod = httpMethod;
        this.requestUri = requestUri;
        this.httpVersion = httpVersion;
        parseQueryString();
    }

    public static RequestLine from(final String header) {
        String[] splitHeaders = header.split(" ");
        return new RequestLine(HttpMethod.valueOf(splitHeaders[0]), splitHeaders[1], splitHeaders[2]);
    }

    public String getUri() {
        return requestUri;
    }

    public boolean containMethod(final HttpMethod method) {
        return httpMethod == method;
    }

    public Map<String, String> getParams() {
        return params;
    }

    private void parseQueryString() {
        if (requestUri.contains("?")) {
            String[] splitRequestUri = requestUri.split("\\?");
            params = HttpRequestUtils.parseQueryString(splitRequestUri[QUERY_STRING]);
            requestUri = splitRequestUri[REQUEST_URI];
        }
    }
}