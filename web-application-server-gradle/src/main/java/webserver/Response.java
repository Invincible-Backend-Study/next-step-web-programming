package webserver;

public class Response <T> {
    private final String httpStatus;
    private final String path;
    private final boolean auth;
    private final T body;

    public Response(String httpStatus, String path, boolean auth, T body) {
        this.httpStatus = httpStatus;
        this.path = path;
        this.auth = auth;
        this.body = body;
    }

    public String getHttpStatus() {
        return httpStatus;
    }

    public String getPath() {
        return path;
    }

    public boolean getAuth() {
        return auth;
    }

    public T getBody() {
        return body;
    }

    @Override
    public String toString() {
        return httpStatus + ", " + path + ", body:" + ((body!=null)?body.toString():"-");
    }
}
