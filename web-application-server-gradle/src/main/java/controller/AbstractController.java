package controller;

import java.io.IOException;
import webserver.http.MyHttpRequest;
import webserver.http.MyHttpResponse;

abstract class AbstractController implements Controller {
    public void service(MyHttpRequest myHttpRequest, MyHttpResponse myHttpResponse) throws IOException {
        if (myHttpRequest.getMethod().isPost()) {
            doPost(myHttpRequest, myHttpResponse);
        }
        if (myHttpRequest.getMethod().isGet()) {
            doGet(myHttpRequest, myHttpResponse);
        }
    }

    void doGet(MyHttpRequest myHttpRequest, MyHttpResponse myHttpResponse) {
    }

    void doPost(MyHttpRequest myHttpRequest, MyHttpResponse myHttpResponse) {
    }
}
