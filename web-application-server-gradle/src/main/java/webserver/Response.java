package webserver;

import java.util.Map;

public class Response {
    private final String httpStatus;
    private final String path;
    private final Map<String, String> headers;
    private final byte[] body;

    public Response(String httpStatus, String path, Map<String, String> headers, byte[] body) {
        this.httpStatus = httpStatus;
        this.path = path;
        this.headers = headers;
        this.body = body;
    }

    public String getHttpStatus() {
        return httpStatus;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public byte[] getBody() {
        return body;
    }

    @Override
    public String toString() {
        return httpStatus + ", " + path + ", body:" + ((body!=null)?body.toString():"-");
    }
}
