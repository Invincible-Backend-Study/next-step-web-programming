package webserver;

import java.util.Map;

public class MyHttpRequest {
    private final String httpMethod;
    private final String requestPath;
    private final Map<String, String> parameters;
    private final Map<String, String> httpHeaders;
    private final String requestBody;

    public MyHttpRequest(String httpMethod, String requestPath, Map<String, String> parameters, Map<String, String> httpHeaders, String requestBody) {
        this.httpHeaders = httpHeaders;
        this.httpMethod = httpMethod;
        this.requestPath = requestPath;
        this.parameters = parameters;
        this.requestBody = requestBody;
    }

    public Map<String, String> getHttpHeaders() {
        return httpHeaders;
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

    public String getRequestBody() {
        return requestBody;
    }

    @Override
    public String toString() {
        return "HttpMethod:" + httpMethod
                + ", requestPath:" + requestPath
                + ", parameters:" + (parameters == null ? "-" : parameters.toString())
                + ", httpHeaders:" + (httpHeaders == null ? "-" : httpHeaders.toString())
                + ", requestBody:" + requestBody;
    }
}
