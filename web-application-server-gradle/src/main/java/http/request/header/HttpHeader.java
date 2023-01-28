package http.request.header;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import util.HttpRequestUtils;


@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpHeader {
    private final Map<String, String> header;

    public static HttpHeader of(final BufferedReader reader) throws IOException {
        return new HttpHeader(parseHttpHeaders(reader));
    }

    private static Map<String, String> parseHttpHeaders(BufferedReader bufferedReader) throws IOException {
        var httpHeaders = new LinkedHashMap<String, String>();

        var line = bufferedReader.readLine();
        while (!"".equals(line)) {
            var pair = HttpRequestUtils.parseHeader(line);
            httpHeaders.put(pair.getKey(), pair.getValue());
            line = bufferedReader.readLine();
        }
        return httpHeaders;
    }

    public String get(String key) {
        return this.header.getOrDefault(key, null);
    }
}
