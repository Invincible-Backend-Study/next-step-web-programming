package controller;

import http.HttpRequest;
import http.HttpResponse;
import util.path.Path;

import java.io.IOException;

public class MainController {

    private final HttpRequest httpRequest;
    private final HttpResponse httpResponse;

    public MainController(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        this.httpRequest = httpRequest;
        this.httpResponse = httpResponse;
    }

    public void runProcess() throws IOException {
        Path url = Path.from(httpRequest.getUrl());
        Controller controller = url.selectController();
        controller.use(httpRequest, httpResponse);
    }

}
