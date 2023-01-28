package http.parser;

import http.HttpRequest;

import java.io.IOException;

public class PostHttpBodyParser implements HttpBodyParser {
    @Override
    public String parseBody(final HttpRequest httpRequest) throws IOException {
        return httpRequest.parseHttpRequestBody();
    }
}
