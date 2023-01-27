package http.request.startline;

import http.exception.HttpExceptionCode;

public class HttpRequestStartLinePolicy {

    public static void validate() {

    }

    public static void validateStartLineLength(final String line) {
        var splitStr = line.split(" ");
        if (splitStr.length != 3) {
            throw HttpExceptionCode.HTTP_START_LINE_SPLIT_SIZE.newInstance(line);
        }
    }
}
