package http.request.startline;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class HttpRequestStartLine {
    private final HttpRequestMethod httpMethod;
    private final String url;
    private final String version;

    //private final String requestParams;

    public static HttpRequestStartLine of(final String line) {
        HttpRequestStartLinePolicy.validateStartLineLength(line);

        var splitStr = line.split(" ");
        var httpRequestMethod = HttpRequestMethod.of(splitStr[0]);
        return new HttpRequestStartLine(httpRequestMethod, splitStr[1], splitStr[2]);
    }

    public static String parserUrl(final String url) {
        var delimiterIndex = url.indexOf('?');

        var requestPath = url;
        if (delimiterIndex != -1) {
            requestPath = url.substring(0, delimiterIndex);
        }
        return requestPath;
    }

}
