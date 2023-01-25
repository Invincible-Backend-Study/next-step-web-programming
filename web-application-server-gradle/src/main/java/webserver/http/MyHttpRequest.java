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
import java.util.Collections;
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

        // RequestLine 가져오기 (Method, RequestPath&Parameter, HttpVer)
        log.debug("RequestLine: {}", lines.get(0));
        String[] tokens = lines.get(0).split(" ");
        String httpMethod = tokens[0];
        String url = tokens[1];
        String httpVersion = tokens[2];

        String requestPath = extractRequestPath(url);
        Map<String, String> headers = extractHeaders(lines);
        String requestBody = extractBody(br, headers.get("Content-Length"));
        Map<String, String> parameters = extractParameters(url, requestBody);

        this.httpMethod = httpMethod;
        this.requestPath = requestPath;
        this.parameters = parameters;
        this.httpHeaders = headers;
        this.requestBody = requestBody;
    }


    private static Map<String, String> extractParameters(String url, String requestBody) {
        Map<String, String> params = extractParametersFromUrl(url);
        if (!params.isEmpty()) {
            return params;
        }
        return extractParametersFromBody(requestBody);
    }

    private static Map<String, String> extractParametersFromBody(String requestBody) {
        if (!"".equals(requestBody)) {
            return HttpRequestUtils.parseQueryString(requestBody);
        }
        return Collections.emptyMap();
    }

    private static Map<String, String> extractParametersFromUrl(String url) {
        int index = url.indexOf("?");
        if (index != -1) {
            return HttpRequestUtils.parseQueryString(url.substring(index + 1));
        }
        return Collections.emptyMap();
    }

    private static String extractRequestPath(String url) {
        String requestPath = url;
        Map<String, String> parameters = null;
        int index = url.indexOf("?");
        if (index != -1) {
            requestPath = url.substring(0, index);
        }
        return requestPath;
    }

    private static String extractBody(BufferedReader br, String contentLength) throws IOException {
        if (contentLength != null) {
            return IOUtils.readData(br, Integer.parseInt(contentLength));
        }
        return null;
    }

    private static Map<String, String> extractHeaders(List<String> lines) {
        Map<String, String> headers = new HashMap<String, String>();
        for (int i = 1; i < lines.size(); i++) {
            String l = lines.get(i);  //TODO l말고 다른 변수명을
            if ("".equals(l)) {
                break;
            }
            String[] headerTokens = l.split(": ");
            if (headerTokens.length == 2) {
                headers.put(headerTokens[0], headerTokens[1]);
            }
        }
        return headers;
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
