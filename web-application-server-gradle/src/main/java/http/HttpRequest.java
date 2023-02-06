package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.IOUtils;
import util.RequestLine;
import webserver.RequestHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private final BufferedReader reader;
    private final RequestLine requestLine;
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private final Map<String, String> headers;

    public HttpRequest(final InputStream in) throws NullPointerException, IOException {
        this.reader = new BufferedReader(new InputStreamReader(in));
        String line = reader.readLine();
        if (line == null) {
            throw new NullPointerException("[ERROR] line contain null");
        }
        this.requestLine = new RequestLine(line);
        this.headers = parseHttpHeaders();
    }

    private Map<String, String> parseHttpHeaders() throws IOException {
        Map<String, String> headers = new HashMap<>();

        String header = reader.readLine();
        while (!"".equals(header)) {
            header = reader.readLine();
            String[] splited = header.split(":");
            if (splited.length >= 2) {
                headers.put(splited[0], splited[1]);
            }
        }
        return headers;
    }


    public String parseHttpRequestBody() throws IOException {
        return IOUtils.readData(reader, Integer.parseInt(headers.get("Content-Length").trim()));
    }

    public String getMethod() {
        return requestLine.getMethod();
    }

    public String getUrl() {
        String url = requestLine.getUrl();
        if (url.contains("?")) {
            return url.substring(0, url.indexOf("?"));
        }
        return requestLine.getUrl();
    }

    public String getParams() {
        String url = requestLine.getUrl();
        if (url.contains("?")) {
            return url.substring(url.indexOf("?") + 1);
        }
        return "";
    }

    public String getHeaders(String headerKey) {
        return headers.get(headerKey);
    }



}
