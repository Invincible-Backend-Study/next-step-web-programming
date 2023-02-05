package core.mvcframework;

public enum HttpMethod {
    GET, POST;

    public boolean isGet() {
        return this == GET;
    }

    public boolean isPost() {
        return this == POST;
    }
}
