package webserver;

import java.util.List;

public class ResponseHeadersMaker {
    public static List<String> ok(int lengthOfBodyContent) {
        return List.of(
                "HTTP/1.1 200 OK",
                "Content-Type: text/html;charset=utf-8",
                "Content-Length: " + lengthOfBodyContent,
                ""
        );
    }

    public static List<String> found(String redirectUrl) {
        return List.of(
                "HTTP/1.1 302 Found",
                "Location: " + redirectUrl,
                ""
        );
    }

    public static List<String> css(int lengthOfBodyContent) {
        return List.of(
                "HTTP/1.1 200 OK",
                "Content-Type: text/css;charset=utf-8",
                "Content-Length: " + lengthOfBodyContent,
                ""
        );
    }
}
