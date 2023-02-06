package util.method;

import exception.HttpIllegalArgumentException;
import http.parser.GetHttpBodyParser;
import http.parser.HttpBodyParser;
import http.parser.PostHttpBodyParser;
import util.path.Parser;

import java.util.Arrays;

public enum HttpMethod implements Parser {
    GET("GET") {
        @Override
        public HttpBodyParser confirmMethod() {
            return  new GetHttpBodyParser();
        }
    },
    POST("POST") {
        @Override
        public HttpBodyParser confirmMethod() {
            return new PostHttpBodyParser();
        }
    };

    private final String method;

    HttpMethod(String method) {
        this.method = method;
    }

    public static HttpMethod from(String method) {
        return Arrays.stream(values())
                .filter(httpMethod -> httpMethod.method.equals(method))
                .findAny()
                .orElseThrow(() -> new HttpIllegalArgumentException("[ERROR]" + method + "지원하지 않는 매서드"));
    }


}
