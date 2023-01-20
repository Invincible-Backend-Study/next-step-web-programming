package webserver;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class Response {
    private final String httpStatus;
    private final String path;
    private final List<String> headers;
    private final byte[] body;

    public Response(String httpStatus, String path, List<String> headers, byte[] body) {
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

    public List<String> getHeaders() {
        return headers;
    }

    public byte[] getBody() {
        return body;
    }

    @Override
    public String toString() {
        return httpStatus + ", " + path + ", headers:" + ((headers!=null)?headers.toString():"-") + ", body:" + ((body!=null)?body.toString():"-");
    }
}
