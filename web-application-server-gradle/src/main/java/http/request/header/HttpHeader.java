package http.request.header;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
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
        var line = bufferedReader.readLine();

        var httpHeaders = new LinkedHashMap<String, String>();
        while (!"".equals(line)) {
            var pair = HttpRequestUtils.parseHeader(line);
            httpHeaders.put(pair.getKey(), pair.getValue());
            line = bufferedReader.readLine();
        }
        return httpHeaders;
    }

    public String get(String key) {
        return this.header.get(key);
    }

    public Optional<String> optionalGet(String key) {
        return Optional.of(this.get(key));
    }
}
