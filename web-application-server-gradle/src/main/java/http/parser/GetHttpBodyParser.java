package http.parser;

import http.HttpRequest;

import java.io.IOException;

public class GetHttpBodyParser implements HttpBodyParser {

    @Override
    public String parseBody(final HttpRequest httpRequest) throws IOException {
        return httpRequest.getParams();
    }
}
