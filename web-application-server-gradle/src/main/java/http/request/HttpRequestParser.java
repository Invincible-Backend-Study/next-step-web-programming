package http.request;

import http.exception.HttpExceptionCode;
import http.request.header.HttpHeader;
import http.request.startline.HttpRequestMethod;
import http.request.startline.HttpRequestStartLine;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HttpRequestParser {
    public static HttpRequest parse(final InputStream inputStream) throws IOException {
        final var bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        final var line = bufferedReader.readLine();

        validateHttpRequestFirstLineIsNull(line);

        var httpStartLine = HttpRequestStartLine.of(line);
        var httpHeaders = HttpHeader.of(bufferedReader);

        // Get Request
        if (httpStartLine.getHttpMethod() == HttpRequestMethod.GET) {
            return new HttpGetRequest(httpStartLine, httpHeaders, bufferedReader);
        }
        // Post Request
        return new HttpPostRequest(httpStartLine, httpHeaders, bufferedReader);
    }

    // http request is null
    private static void validateHttpRequestFirstLineIsNull(String line) {
        if (line == null) {
            throw HttpExceptionCode.HTTP_START_LINE_IS_NULL.newInstance();
        }
    }


}
