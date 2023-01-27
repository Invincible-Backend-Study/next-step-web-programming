package controller;

import java.io.IOException;
import webserver.http.MyHttpRequest;
import webserver.http.MyHttpResponse;

public interface Controller {
    void service(MyHttpRequest myHttpRequest, MyHttpResponse myHttpResponse) throws IOException;
}
