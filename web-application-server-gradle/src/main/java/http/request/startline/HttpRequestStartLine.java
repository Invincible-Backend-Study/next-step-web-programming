package http.request.startline;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpRequestStartLine {
    private final HttpRequestMethod httpMethod;
    private final String url;
    private final String version;

    //private final String requestParams;

    public static HttpRequestStartLine of(final String line) {
        HttpRequestStartLinePolicy.validateStartLineLength(line);

        var splitStr = line.split(" ");
        var httpRequestMethod = HttpRequestMethod.of(splitStr[0]);
        return new HttpRequestStartLine(httpRequestMethod, parserUrl(splitStr[1]), splitStr[2]);
    }

    public static String parserUrl(final String url) {
        var delimiterIndex = url.indexOf('?');

        if (delimiterIndex != -1) {
            return url.substring(0, delimiterIndex);
        }
        return url;
    }

}
