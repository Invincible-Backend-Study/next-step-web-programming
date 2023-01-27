package controller;

import webserver.http.MyHttpRequest;
import webserver.http.Response;

abstract class AbstractController implements Controller {
    Response doGet(MyHttpRequest myHttpRequest) {
        return null;
    }

    Response doPost(MyHttpRequest myHttpRequest) {
        return null;
    }

}
