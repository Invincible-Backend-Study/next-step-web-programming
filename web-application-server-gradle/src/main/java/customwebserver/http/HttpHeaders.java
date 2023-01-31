package customwebserver.http;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpHeaders {
    public static final String CONTENT_LENGTH = "Content-Length";
    private final Map<String, String> httpHeaders = new HashMap<>();

    public HttpHeaders() {
    }

    public HttpHeaders(final Map<String, String> httpHeaders) {
        this.httpHeaders.putAll(httpHeaders);
    }

    public String getHeader(final String headerName) {
        if (httpHeaders.containsKey(headerName)) {
            return httpHeaders.get(headerName);
        }
        throw new IllegalArgumentException("[ERROR] No Header about " + headerName);
    }

    public int getContentLength() {
        if (httpHeaders.containsKey(CONTENT_LENGTH)) {
            return Integer.parseInt(httpHeaders.get(CONTENT_LENGTH));
        }
        throw new IllegalArgumentException("[ERROR] No Header about 'Content-Length'");
    }

    public void addHeader(final String headerName, final String headerValue) {
        httpHeaders.put(headerName, headerValue);
    }

    public String createHeaderMessage() {
        return httpHeaders.keySet()
                .stream()
                .map(key -> key + ": " + httpHeaders.get(key) + "\r\n")
                .collect(Collectors.joining(""));
    }
}
