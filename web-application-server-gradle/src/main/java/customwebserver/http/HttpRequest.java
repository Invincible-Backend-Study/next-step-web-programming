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
    private static final int HTTP_METHOD = 0;
    private static final int URI = 1;
    private static final int QUERY_STRING = 1;
    private static final int REQUEST_URI = 0;
    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    private final HttpMethod httpMethod;
    private String requestUri;
    private final Map<String, String> requestHeader;
    private Map<String, String> queryStrings;

    public HttpRequest(final InputStream in) throws IOException {
        BufferedReader httpRequestReader = new BufferedReader(new InputStreamReader(in));
        String[] requestLine = parseRequestLine(httpRequestReader.readLine());
        httpMethod = HttpMethod.valueOf(requestLine[HTTP_METHOD]);
        requestUri = requestLine[URI];
        requestHeader = parseHttpRequestHeader(httpRequestReader);
        queryStrings = parseQueryString(httpRequestReader);
    }

    public String getRequestUri() {
        return requestUri;
    }

    public boolean containMethod(final HttpMethod method) {
        return httpMethod == method;
    }

    /**
     * url에 붙은 쿼리스트링 값이 없다면, form 데이터 조회
     */
    public String getParameter(final String parameterName) {
        if (queryStrings == null) {
            throw new IllegalArgumentException("[ERROR] No Parameter in request");
        }
        return queryStrings.get(parameterName);
    }

    public String getCookie(final String cookieName) {
        Map<String, String> cookies = HttpRequestUtils.parseCookies(requestHeader.get("Cookie"));
        return cookies.get(cookieName);
    }

    private Map<String, String> parseQueryString(final BufferedReader httpRequestReader) throws IOException {
        if (httpMethod.equals(HttpMethod.GET)) {
            return parseQueryStringByUri();
        }
        if (httpMethod.equals(HttpMethod.POST)) {
            return parseQueryStringByForm(httpRequestReader);
        }
        return null;
    }


    private String[] parseRequestLine(final String requestLine) {
        return requestLine.split(" ");
    }

    private Map<String, String> parseQueryStringByUri() {
        String[] splitRequestUri = requestUri.split("\\?");
        if (splitRequestUri.length == 1) {
            return null;
        }
        requestUri = splitRequestUri[REQUEST_URI];
        log.debug("requestUri={}", requestUri);
        return HttpRequestUtils.parseQueryString(splitRequestUri[QUERY_STRING]);
    }

    private Map<String, String> parseQueryStringByForm(final BufferedReader httpRequestReader) throws IOException {
        if (requestHeader.containsKey("Content-Length")) {
            String formData = IOUtils.readData(httpRequestReader,
                    Integer.parseInt(requestHeader.get("Content-Length")));
            log.debug("POST form Data={}", formData);
            return HttpRequestUtils.parseQueryString(formData);
        }
        return null;
    }

    private Map<String, String> parseHttpRequestHeader(final BufferedReader httpRequestReader)
            throws IOException {
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
