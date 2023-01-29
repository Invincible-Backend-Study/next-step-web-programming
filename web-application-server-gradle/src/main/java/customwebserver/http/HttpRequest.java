package customwebserver.http;

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
import utils.IOUtils;
import utils.dto.Pair;
import utils.enums.HttpMethod;

public class HttpRequest {
    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    private final BufferedReader httpRequest;
    private RequestLine requestLine;
    private Map<String, String> requestHeader;
    private Map<String, String> params;

    public HttpRequest(final InputStream in) throws IOException {
        httpRequest = new BufferedReader(new InputStreamReader(in));
        requestLine = HttpRequestUtils.parseRequestLine(httpRequest.readLine());
        requestHeader = parseHttpRequestHeader();
        setParams();
    }

    private void setParams() throws IOException {
        if (requestLine.containMethod(HttpMethod.GET)) {
            params = requestLine.getParams();
        }
        params = parseQueryStringByForm();
    }

    public String getPath() {
        return requestLine.getUri();
    }

    public boolean containMethod(final HttpMethod method) {
        return requestLine.containMethod(method);
    }

    public String getHeader(final String headerName) {
        String header = requestHeader.get(headerName);
        if (header == null) {
            throw new IllegalArgumentException("[ERROR] No header in request");
        }
        return header;
    }

    public String getParameter(final String parameterName) {
        return params.get(parameterName);
    }

    public String getCookie(final String cookieName) {
        Map<String, String> cookies = HttpRequestUtils.parseCookies(requestHeader.get("Cookie"));
        return cookies.get(cookieName);
    }

    private Map<String, String> parseQueryStringByForm() throws IOException {
        if (requestHeader.containsKey("Content-Length")) {
            String formData = IOUtils.readData(httpRequest,
                    Integer.parseInt(requestHeader.get("Content-Length")));
            log.debug("POST form Data={}", formData);
            return HttpRequestUtils.parseQueryString(formData);
        }
        return null;
    }

    private Map<String, String> parseHttpRequestHeader() throws IOException {
        String header = httpRequest.readLine();
        List<Pair> headerPairs = new ArrayList<>();
        while (!header.equals("")) {
            headerPairs.add(HttpRequestUtils.parseHeader(header));
            header = httpRequest.readLine();
        }
        return headerPairs.stream()
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }
}
