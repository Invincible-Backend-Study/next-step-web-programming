package utils.dto;

import utils.enums.HttpStatus;

public class RequestLine {
    private final HttpStatus httpStatus;
    private final String requestUri;
    private final String httpVersion;

    private RequestLine(final HttpStatus httpStatus, final String requestUri, final String httpVersion) {
        this.httpStatus = httpStatus;
        this.requestUri = requestUri;
        this.httpVersion = httpVersion;
    }

    public static RequestLine from(final String header) {
        String[] splitHeaders = header.split(" ");
        return new RequestLine(HttpStatus.getStatus(Integer.parseInt(splitHeaders[0])), splitHeaders[1], splitHeaders[2]);
    }
}