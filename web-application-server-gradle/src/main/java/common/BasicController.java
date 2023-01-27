package common;

import http.request.HttpRequest;
import http.response.HttpResponse;
import java.io.IOException;

@FunctionalInterface
public interface BasicController {
    void process(final HttpRequest httpRequest, final HttpResponse response) throws IOException;
}
