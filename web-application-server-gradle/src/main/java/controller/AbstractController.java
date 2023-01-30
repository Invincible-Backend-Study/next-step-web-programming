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

    /**
     * abstract는 좋지 않은것 같음..!
     * 사용자가 원하는 메소드만 오버라이딩 할 수 있도록하고 default는 4xx오류페이지를 보여주는게 알맞은 방향같다..!
     */
    protected void doGet(final HttpRequest request, final HttpResponse response) throws IOException {
        // 404
    }

    protected void doPost(final HttpRequest request, final HttpResponse response) throws IOException {

    }
}
