package http.request;

import http.request.header.HttpHeader;
import http.request.startline.HttpRequestStartLine;
import java.io.BufferedReader;

public class HttpGetRequest extends HttpRequest {
    public HttpGetRequest(HttpRequestStartLine httpStartLine, HttpHeader httpHeaders, BufferedReader bufferedReader) {
        super(httpStartLine, httpHeaders, null);
    }

    // request url parsing
}
