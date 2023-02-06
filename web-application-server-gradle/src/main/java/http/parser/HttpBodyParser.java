package http.parser;

import http.HttpRequest;

import java.io.IOException;

public interface HttpBodyParser {
    String parseBody(final HttpRequest httpRequest) throws IOException;
}
