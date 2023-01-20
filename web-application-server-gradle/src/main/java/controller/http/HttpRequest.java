package controller.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpRequestUtils;
import utils.dto.Pair;
import utils.dto.RequestLine;
import utils.enums.HttpMethod;

public class HttpRequest {
    public static final String EMPTY = "";
    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    private final BufferedReader httpRequest;
    private RequestLine requestLine;
    private Map<String, String> requestHeaderKeyValue;

    public HttpRequest(final InputStream in) throws IOException {
        httpRequest = new BufferedReader(new InputStreamReader(in));
        requestLine = HttpRequestUtils.parseRequestLine(httpRequest.readLine());
        requestHeaderKeyValue = parseHttpRequestHeaderKeyValue(httpRequest);
    }

    private Map<String, String> parseHttpRequestHeaderKeyValue(final BufferedReader httpRequest) throws IOException {
        String header = httpRequest.readLine();
        List<Pair> headerPairs = new ArrayList<>();
        while (!header.equals(EMPTY)) {
            headerPairs.add(HttpRequestUtils.parseHeader(header));
            header = httpRequest.readLine();
        }
        return headerPairs.stream()
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }


    public String getRequestUri() {
        return requestLine.getUri();
    }

    public boolean containMethod(final HttpMethod method) {
        return requestLine.containMethod(method);
    }

    public String getParameter(final String userId) {
        return requestLine.getQueryString(userId);
    }
}
