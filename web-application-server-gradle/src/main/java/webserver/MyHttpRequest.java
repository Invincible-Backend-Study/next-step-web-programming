package webserver;

import java.util.Map;

public class MyHttpRequest {
    private final Map<String, String> httpHeaders;
    private final String httpMethod;
    private final String requestPath;
    private final Map<String, String> parameters;
    // private final String requestBody;

    public MyHttpRequest(Map<String, String> httpHeaders, String httpMethod, String requestPath, Map<String, String> parameters) {
        this.httpHeaders = httpHeaders;
        this.httpMethod = httpMethod;
        this.requestPath = requestPath;
        this.parameters = parameters;
        // this.requestBody = requestBody;
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

    @Override
    public String toString() {
        return "HttpMethod:" + httpMethod
                + ", httpHeaders:" + (httpHeaders == null ? "-" : httpHeaders.toString())
                + ", requestPath:" + requestPath
                + ", parameters:" + (parameters == null ? "-" : parameters.toString());
                //+ ", requestBody:" + (requestBody == null ? "-" : requestBody.toString());
    }
}
