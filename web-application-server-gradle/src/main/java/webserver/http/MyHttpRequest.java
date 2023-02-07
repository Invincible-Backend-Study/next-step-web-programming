package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;

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
    private static final Logger log = LoggerFactory.getLogger(MyHttpRequest.class);
    private static final int REQUEST_LINE = 0;
    private static final int HEADER_START_LINE = 1;

    private final RequestLine requestLine;
    private final Map<String, String> parameters;
    private final Map<String, String> headers;
    private final Map<String, String> cookies;
    private final MyHttpSession session;
    private final String body;

    public MyHttpRequest(InputStream in) throws IOException {
        // BufferedReader 값 읽기
        InputStreamReader reader = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(reader);
        List<String> lines = readInputStream(br);

        // 변수 초기화
        requestLine = new RequestLine(lines.get(REQUEST_LINE));
        headers = extractHeaders(lines);
        cookies = extractCookies(headers.get("Cookie"));
        session = getSession(getSessionId());
        body = extractBody(br, headers.get("Content-Length"));
        parameters = extractParameters(requestLine.getParams(), body);
        logWithoutStyle();
    }

    private static Map<String, String> extractParameters(Map<String, String> requestLineParams, String requestBody) {
        // url 또는 body에 있는 params를 반환한다.
        if (!requestLineParams.isEmpty()) {
            return requestLineParams;
        }
        return extractParametersFromBody(requestBody);
    }

    private static Map<String, String> extractParametersFromBody(String requestBody) {
        if (!"".equals(requestBody)) {
            return HttpRequestUtils.parseQueryString(requestBody);
        }
        return Collections.emptyMap();
    }

    private static String extractBody(BufferedReader br, String contentLength) throws IOException {
        if (contentLength != null) {
            return IOUtils.readData(br, Integer.parseInt(contentLength));
        }
        return null;
    }

    private static Map<String, String> extractHeaders(List<String> lines) {
        Map<String, String> headers = new HashMap<String, String>();
        for (int i = HEADER_START_LINE; i < lines.size(); i++) {
            String line = lines.get(i);
            if ("".equals(line)) {
                break;
            }
            String[] headerTokens = line.split(": ");
            if (headerTokens.length == 2) {
                headers.put(headerTokens[0], headerTokens[1]);
            }
        }
        return headers;
    }

    private static Map<String, String> extractCookies(String cookies) {
        if (cookies == null) {
            return null;
        }
        return HttpRequestUtils.parseCookies(cookies);
    }

    private static MyHttpSession getSession(String uuid) {
        log.debug("SessionId: {}", uuid);
        if (uuid == null) {
            return null;
        }
        return HttpSessions.get(uuid);
    }

    private static List<String> readInputStream(BufferedReader br) throws IOException {
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null && !"".equals(line)) {
            lines.add(line);
            // log.debug("BufferedReader: {}", line);
        }
        return lines;
    }

    public Map<String, String> getAllHeaders() {
        return headers;
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public String getCookie(String key) {
        if (cookies == null) {
            return null;
        }
        return cookies.get(key);
    }

    public MyHttpSession getSession() {
        return session;
    }

    public String getSessionId() {
        return getCookie("JSESSIONID");
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getRequestPath() {
        return requestLine.getPath();
    }

    public Map<String, String> getAllParameters() {
        return parameters;
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "Http:" + requestLine.getMethod()
                + " " + requestLine.getPath()
                + ", Parameters: " + (parameters == null ? "-" : parameters.toString())
                + "\n Headers:" + (headers == null ? "-" : headers.toString())
                + "\n Cookie:" + (cookies == null ? "-" : cookies.toString())
                + "\n Body:" + body;
    }

    private void logWithoutStyle() {
        if (requestLine.getPath().endsWith(".css") || requestLine.getPath().endsWith(".js") || requestLine.getPath().endsWith(".ico") || requestLine.getPath().endsWith(".woff")) {
            return;
        }
        log.debug("Http: {} {}  Parameters: {}", requestLine.getMethod(), requestLine.getPath(), (parameters == null ? "-" : parameters.toString()));
        log.debug("Headers: {}", (headers == null ? "-" : headers.toString()));
        log.debug("Cookie: {}", (cookies == null ? "-" : cookies.toString()));
        log.debug("Session: {}", (session == null ? "-" : session.toString()));
        log.debug("Body: {}", body);
    }
}
