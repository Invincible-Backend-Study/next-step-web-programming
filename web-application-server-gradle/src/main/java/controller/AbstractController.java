package controller;

import customwebserver.http.HttpRequest;
import customwebserver.http.HttpResponse;
import java.io.IOException;
import utils.enums.HttpMethod;

public abstract class AbstractController implements Controller {

    @Override
    public void service(final HttpRequest request, final HttpResponse response) throws IOException {
        if (request.containMethod(HttpMethod.GET)) {
            doGet(request, response);
            return;
        }
        if (request.containMethod(HttpMethod.POST)) {
            doPost(request, response);
        }
    }

    protected void doGet(final HttpRequest request, final HttpResponse response) throws IOException {
        throw new RuntimeException("[ERROR] Undefined URI about GET Method");
    }

    protected void doPost(final HttpRequest request, final HttpResponse response) throws IOException {
        throw new RuntimeException("[ERROR] Undefined URI about POST Method");

    }
}
