package controller;

import controller.http.HttpRequest;
import controller.http.HttpResponse;

public interface Controller {
    /**
     * @return 정상적인 처리가 완료됐다면 true를 반환한다.
     */
    boolean doGet(HttpRequest httpRequest, HttpResponse httpResponse);

    boolean doPost(HttpRequest httpRequest, HttpResponse httpResponse);
}
