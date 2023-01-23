package webserver.http;

import java.util.List;

public class Response {
    private final List<String> headers;
    private final byte[] body;

    public Response(List<String> headers, byte[] body) {
        this.headers = headers;
        this.body = body;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public byte[] getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "headers:" + ((headers != null) ? headers.toString() : "-") + ", body:" + ((body != null) ? body.toString() : "-");
    }
}
