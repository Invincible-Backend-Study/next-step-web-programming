package http.request;

import http.request.header.HttpHeader;
import http.request.startline.HttpRequestStartLine;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import util.HttpRequestUtils;
import util.IOUtils;


public class HttpPostRequest extends HttpRequest {
    public HttpPostRequest(HttpRequestStartLine httpStartLine,
                           HttpHeader httpHeaders,
                           BufferedReader bufferedReader
    ) throws IOException {
        super(httpStartLine, httpHeaders, parseHttpBody(bufferedReader, Integer.parseInt(httpHeaders.get("Content-Length"))));
    }

    private static Map<String, String> parseHttpBody(BufferedReader bufferedReader, int contentLength)
            throws IOException {
        return HttpRequestUtils.parseQueryString(IOUtils.readData(bufferedReader, contentLength));
    }

}
