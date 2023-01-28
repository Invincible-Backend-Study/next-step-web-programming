package http.request;

import static java.net.URLDecoder.decode;

import http.request.header.HttpHeader;
import http.request.startline.HttpRequestStartLine;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import util.HttpRequestUtils;


public abstract class HttpRequest {

    private final HttpRequestStartLine httpRequestStartLine;
    private final HttpHeader httpHeaders;
    private final Map<String, String> httpBody;

    private final Map<String, String> cookies;

    public HttpRequest(HttpRequestStartLine httpRequestStartLine, HttpHeader httpHeaders, Map<String, String> httpBody) {
        this.httpRequestStartLine = httpRequestStartLine;
        this.httpHeaders = httpHeaders;
        this.httpBody = httpBody;
        this.cookies = HttpRequestUtils.parseCookies(httpHeaders.get("Cookie"));
    }

    public HttpHeader getHttpHeader() {
        return this.httpHeaders;
    }

    public String getParameterByKey(String key) {
        // encoding 값 들을 decoding
        return this.httpBody.getOrDefault(decode(key, StandardCharsets.UTF_8), null);
    }

    public Optional<String> getCookie(String key) {
        return Optional.ofNullable(cookies.getOrDefault(key, null));
    }

    public String getRequestPath() {
        return this.httpRequestStartLine.getUrl();
    }
}
