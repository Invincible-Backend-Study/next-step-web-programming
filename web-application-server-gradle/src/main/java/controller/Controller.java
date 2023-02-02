package controller;

import customwebserver.http.HttpRequest;
import customwebserver.http.HttpResponse;
import java.io.IOException;

public interface Controller {
    void service(HttpRequest request, HttpResponse response) throws IOException;

}
