package controller;

import customwebserver.http.HttpRequest;
import customwebserver.http.HttpResponse;
import java.io.IOException;

public interface Controller {
    /**
     * @return 정상적인 처리가 완료됐다면 true를 반환한다.
     */
    boolean doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException;

    boolean doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException;
}
