package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;
import webserver.RequestHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyHttpRequest {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

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

    public MyHttpRequest(InputStream in) throws IOException {
        // BufferedReader 값 읽기
        InputStreamReader reader = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(reader);

        List<String> lines = readInputStream(br);

        // RequestLine 가져오기
        String[] tokens = lines.get(0).split(" ");
        lines.remove(0);
        String httpMethod = tokens[0];
        String url = tokens[1];

        // request path & parameter 읽기
        String requestPath = url;
        Map<String, String> parameters = null;
        int index = url.indexOf("?");
        if (index != -1) {
            requestPath = url.substring(0, index);
            String params = url.substring(index + 1);

            // parameter map으로 분리
            parameters = HttpRequestUtils.parseQueryString(params);
        }

        // header 읽기
        Map<String, String> headers = new HashMap<String, String>();
        for (String l : lines) {
            if ("".equals(l)) {
                break;
            }
            String[] headerTokens = l.split(": ");
            if (headerTokens.length == 2) {
                headers.put(headerTokens[0], headerTokens[1]);
            }
        }
        log.debug("contentLength:" + headers.get("Content-Length"));

        // request body 읽기
        String requestBody = null;
        if (headers.get("Content-Length") != null) {
            requestBody = IOUtils.readData(br, Integer.parseInt(headers.get("Content-Length")));
            if (!"".equals(requestBody)) {
                log.debug("requestBody: " + requestBody);
                if ("POST".equals(httpMethod)) {
                    parameters = HttpRequestUtils.parseQueryString(requestBody);
                }
            }
        }

        this.httpMethod = httpMethod;
        this.requestPath = requestPath;
        this.parameters = parameters;
        this.httpHeaders = headers;
        this.requestBody = requestBody;
    }

    private static List<String> readInputStream(BufferedReader br) throws IOException {
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null && !"".equals(line)) {
            lines.add(line);
            log.debug("BufferedReader: " + line);
        }
        return lines;
    }

    public Map<String, String> getAllHeaders() {
        return httpHeaders;
    }

    public String getHeader(String key) {
        return httpHeaders.get(key);
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public Map<String, String> getAllParameters() {
        return parameters;
    }

    public String getParameter(String key) {
        return parameters.get(key);
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
