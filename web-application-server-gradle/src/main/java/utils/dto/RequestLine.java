package utils.dto;

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
}