package http.request;

import http.exception.HttpExceptionCode;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HttpRequestParser {
    public static HttpRequest parse(final InputStream inputStream) throws IOException {
        var bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        final var line = bufferedReader.readLine();
        
        validateHttpRequestFirstLineIsNull(line);
        return new HttpPostRequest();
    }

    // http request is null
    private static void validateHttpRequestFirstLineIsNull(String line) {
        if (line == null) {
            throw HttpExceptionCode.HTTP_START_LINE_IS_NULL.newInstance();
        }
    }
}
