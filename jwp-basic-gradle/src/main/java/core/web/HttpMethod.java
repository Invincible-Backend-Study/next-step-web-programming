package core.web;

import java.util.Arrays;

@Deprecated
public enum HttpMethod {
    GET("GET"),
    POST("POST"),
    DELETE("DELETE"),
    PUT("PUT"),
    UPDATE("UPDATE");

    private final String name;

    HttpMethod(String name) {
        this.name = name;
    }

    public static HttpMethod from(String name) {
        return Arrays.stream(HttpMethod.values())
                .filter(method -> method.getName().equals(name))
                .findAny()
                .orElse(null);
    }

    public boolean isGet() {
        return this == GET;
    }

    public boolean isPost() {
        return this == POST;
    }

    public boolean isDelete() {
        return this == DELETE;
    }

    public boolean isPut() {
        return this == PUT;
    }

    public boolean isUpdate() {
        return this == UPDATE;
    }

    private String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
