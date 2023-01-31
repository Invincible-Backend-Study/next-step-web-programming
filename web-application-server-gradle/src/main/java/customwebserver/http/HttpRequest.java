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

    private final RequestLine requestLine;
    private final HttpHeaders httpHeaders;
    private final RequestParameters requestParameters = new RequestParameters();

    public HttpRequest(final InputStream in) throws IOException {
        BufferedReader httpRequestReader = new BufferedReader(new InputStreamReader(in));
        requestLine = RequestLine.from(httpRequestReader.readLine());
        httpHeaders = new HttpHeaders(parseHttpRequestHeader(httpRequestReader));
        requestParameters.addParams(requestLine.getParams());
        addFormParams(httpRequestReader);
    }

    private void addFormParams(final BufferedReader httpRequestReader) throws IOException {
        if (requestLine.containMethod(HttpMethod.POST)) {
            requestParameters.addParams(parseQueryStringByForm(httpRequestReader));
        }
    }

    public boolean containMethod(final HttpMethod method) {
        return requestLine.containMethod(method);
    }

    public String getHeader(final String headerName) {
        String header = httpHeaders.getHeader(headerName);
        if (header == null) {
            throw new IllegalArgumentException("[ERROR] No header in request");
        }
        return header;
    }

    public String getPath() {
        return requestLine.getUri();
    }

    public String getParameter(final String parameterName) {
        return requestParameters.getParameter(parameterName);
    }

    public String getCookie(final String cookieName) {
        Map<String, String> cookies = HttpRequestUtils.parseCookies(httpHeaders.getHeader("Cookie"));
        return cookies.get(cookieName);
    }

    private Map<String, String> parseQueryStringByForm(final BufferedReader httpRequestReader) throws IOException {
        String formData = IOUtils.readData(httpRequestReader, httpHeaders.getContentLength());
        log.debug("POST form Data={}", formData);
        return HttpRequestUtils.parseQueryString(formData);
    }

    private Map<String, String> parseHttpRequestHeader(final BufferedReader httpRequestReader) throws IOException {
        String header = httpRequestReader.readLine();
        List<Pair> headerPairs = new ArrayList<>();
        while (!header.equals("")) {
            headerPairs.add(HttpRequestUtils.parseHeader(header));
            header = httpRequestReader.readLine();
        }
        return headerPairs.stream()
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }
}
