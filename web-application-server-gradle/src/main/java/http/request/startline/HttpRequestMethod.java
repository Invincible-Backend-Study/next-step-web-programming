package http.request.startline;

import http.exception.HttpExceptionCode;
import java.util.Arrays;

public enum HttpRequestMethod {
    GET,
    POST;

    public static HttpRequestMethod of(String input) {
        return Arrays.stream(values())
                .filter(httpRequestMethod -> httpRequestMethod.name().equals(input.toUpperCase()))
                .findAny()
                .orElseThrow(() -> HttpExceptionCode.HTTP_REQUEST_METHOD_DID_NOT_EXISTS.newInstance(input));
    }
}
