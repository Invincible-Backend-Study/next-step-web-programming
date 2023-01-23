package customwebserver.http;

import java.util.Map;
import java.util.Objects;
import utils.HttpRequestUtils;
import utils.enums.HttpMethod;

public class RequestLine {
    public static final int QUERY_STRING = 1;
    public static final int REQUEST_URI = 0;

    private final HttpMethod httpMethod;
    private String requestUri;
    private Map<String, String> queryStrings;
    private final String httpVersion;

    private RequestLine(final HttpMethod httpMethod, final String requestUri, final String httpVersion) {
        this.httpMethod = httpMethod;
        this.requestUri = requestUri;
        this.httpVersion = httpVersion;
        parseQueryString();
    }

    private void parseQueryString() {
        if (requestUri.contains("?")) {
            String[] splitRequestUri = requestUri.split("\\?");
            queryStrings = HttpRequestUtils.parseQueryString(splitRequestUri[QUERY_STRING]);
            requestUri = splitRequestUri[REQUEST_URI];
        }
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

    public String getQueryString(final String parameterName) {
        if (queryStrings == null || !queryStrings.containsKey(parameterName)) {
            return null;
        }
        return queryStrings.get(parameterName);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RequestLine that = (RequestLine) o;
        return httpMethod == that.httpMethod && requestUri.equals(that.requestUri) && httpVersion.equals(
                that.httpVersion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpMethod, requestUri, httpVersion);
    }

    @Override
    public String toString() {
        return "RequestLine{" +
                "httpMethod=" + httpMethod +
                ", requestUri='" + requestUri + '\'' +
                ", queryStrings=" + queryStrings +
                ", httpVersion='" + httpVersion + '\'' +
                '}';
    }
}