package util;

import java.util.Arrays;

public class RequestLine {

    private final String method;
    private final String url;
    private final String model;

    public RequestLine(String line) {
        String[] splitedRequestLine = line.split(" ");
        this.method = splitedRequestLine[0];
        this.url = splitedRequestLine[1];
        this.model = splitedRequestLine[2];
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }
}
