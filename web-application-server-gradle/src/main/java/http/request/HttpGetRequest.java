package http.request;

import http.request.header.HttpHeader;
import http.request.startline.HttpRequestStartLine;
import java.io.BufferedReader;
import java.util.Map;
import java.util.Optional;
import util.HttpRequestUtils;

public class HttpGetRequest implements HttpRequest {
    private final HttpRequestStartLine httpRequestStartLine;
    private final HttpHeader httpHeaders;
    private final Map<String, String> httpBody;

    public HttpGetRequest(HttpRequestStartLine httpStartLine, HttpHeader httpHeaders, BufferedReader bufferedReader) {
        this.httpRequestStartLine = httpStartLine;
        this.httpHeaders = httpHeaders;
        this.httpBody = null;
    }

    @Override
    public HttpRequestStartLine getStartLine() {
        return this.httpRequestStartLine;
    }

    @Override
    public HttpHeader getHttpHeader() {
        return this.httpHeaders;
    }

    @Override
    public Map<String, String> getParameters() {
        return null;
    }


    @Override
    public String getParameterByKey(String key) {
        return this.httpBody.getOrDefault(key, null);
    }

    @Override
    public Optional<String> getCookie(String key) {
        return Optional.ofNullable(HttpRequestUtils.parseCookies(httpHeaders.get("Cookie")).getOrDefault(key, null));
    }
}
