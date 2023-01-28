package controller;

import http.HttpRequest;
import http.HttpResponse;

import java.io.IOException;

public class HomeController implements Controller {

    @Override
    public void use(final HttpRequest httpRequest, final HttpResponse httpResponse) throws IOException {
        httpResponse.responseStaticFile(httpRequest);
    }
}
