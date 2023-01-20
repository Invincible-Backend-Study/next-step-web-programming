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
import utils.dto.RequestLine;
import utils.enums.HttpMethod;

public class HttpRequest {
    public static final String EMPTY = "";
    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    private final BufferedReader httpRequest;
    private RequestLine requestLine;
    private Map<String, String> requestHeaderKeyValue;
    private Map<String, String> queryStringByForm;

    public HttpRequest(final InputStream in) throws IOException {
        httpRequest = new BufferedReader(new InputStreamReader(in));
        requestLine = HttpRequestUtils.parseRequestLine(httpRequest.readLine());
        requestHeaderKeyValue = parseHttpRequestHeaderKeyValue();
        queryStringByForm = parseQueryStringByForm();
    }

        private Map<String, String> parseQueryStringByForm() throws IOException {
        if (requestHeaderKeyValue.containsKey("Content-Length")) {
            String formData = IOUtils.readData(httpRequest, Integer.parseInt(requestHeaderKeyValue.get("Content-Length")));
            log.info("POST form Data={}", formData);
            return HttpRequestUtils.parseQueryString(formData);
        }
        return null;
    }

    private Map<String, String> parseHttpRequestHeaderKeyValue() throws IOException {
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

    /**
     * url에 붙은 쿼리스트링 값이 없다면, form 데이터 조회
     */
    public String getParameter(final String parameterName) {
        String urlQueryString = requestLine.getQueryString(parameterName);
        if (urlQueryString != null) {
            return urlQueryString;
        }
        return queryStringByForm.get(parameterName);
    }
}
