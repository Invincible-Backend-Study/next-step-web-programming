package utils.dto;

import java.util.Objects;
import utils.enums.HttpMethod;

public class RequestLine {
    private final HttpMethod httpMethod;
    private final String requestUri;
    private final String httpVersion;

    private RequestLine(final HttpMethod httpMethod, final String requestUri, final String httpVersion) {
        this.httpMethod = httpMethod;
        this.requestUri = requestUri;
        this.httpVersion = httpVersion;
    }

    public static RequestLine from(final String header) {
        String[] splitHeaders = header.split(" ");
        return new RequestLine(HttpMethod.valueOf(splitHeaders[0]), splitHeaders[1], splitHeaders[2]);
    }

    public String getUri() {
        return requestUri;
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
}