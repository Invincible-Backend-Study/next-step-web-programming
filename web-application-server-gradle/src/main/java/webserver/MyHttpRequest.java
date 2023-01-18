package webserver;

import java.util.Map;

public class MyHttpRequest {
    private final String httpMethod;
    private final String requestPath;
    private final Map<String, String> parameters;

    public MyHttpRequest(String httpMethod, String requestPath, Map<String, String> parameters) {
        this.httpMethod = httpMethod;
        this.requestPath = requestPath;
        this.parameters = parameters;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    @Override
    public String toString() {
        return "HttpMethod:" + httpMethod
                + ", requestPath:" + requestPath
                + ", parameters:" + (parameters == null ? "-" : parameters.toString());
    }
}
