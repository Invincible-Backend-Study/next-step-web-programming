package controller;

import java.io.IOException;
import webserver.http.MyHttpRequest;
import webserver.http.MyHttpResponse;
import webserver.http.Response;

abstract class AbstractController implements Controller {
    public void service(MyHttpRequest myHttpRequest, MyHttpResponse myHttpResponse) throws IOException {
    }

    Response doGet(MyHttpRequest myHttpRequest) {
        return null;
    }

    Response doPost(MyHttpRequest myHttpRequest) {
        return null;
    }

}
