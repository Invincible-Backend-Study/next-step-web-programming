package controller;

import webserver.http.MyHttpRequest;
import webserver.http.MyHttpResponse;

public class DefaultController extends AbstractController {
    @Override
    public void service(MyHttpRequest myHttpRequest, MyHttpResponse myHttpResponse) {
        String path = myHttpRequest.getRequestPath();
        myHttpResponse.forward(path);
    }
}
