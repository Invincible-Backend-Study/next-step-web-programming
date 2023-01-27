package http.request;

import http.request.header.HttpHeader;
import http.request.startline.HttpRequestStartLine;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import util.HttpRequestUtils;
import util.IOUtils;


public class HttpPostRequest implements HttpRequest {
    private final HttpRequestStartLine httpRequestStartLine;
    private final HttpHeader httpHeaders;
    private final Map<String, String> httpBody;

    public HttpPostRequest(HttpRequestStartLine httpStartLine, HttpHeader httpHeaders,
                           BufferedReader bufferedReader) throws IOException {
        this.httpRequestStartLine = httpStartLine;
        this.httpHeaders = httpHeaders;
        this.httpBody = parseHttpBody(bufferedReader, Integer.parseInt(this.httpHeaders.get("Content-Length")));
    }

    private static Map<String, String> parseHttpBody(BufferedReader bufferedReader, int contentLength)
            throws IOException {
        return HttpRequestUtils.parseQueryString(IOUtils.readData(bufferedReader, contentLength));
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
        return this.httpBody;
    }

    @Override
    public String getParameterByKey(String key) {
        return this.httpBody.getOrDefault(key, null);
    }

    @Override
    public Optional<String> getCookie(String key) {
        return Optional.empty();
    }

}
