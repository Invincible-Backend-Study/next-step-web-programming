package controller;

import http.HttpRequest;
import http.HttpResponse;

import java.io.IOException;

public interface Controller {
    void use(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException;
}
