package webserver;

import controller.WebpageController;
import webserver.http.HttpMethod;
import webserver.http.MyHttpRequest;
import webserver.http.MyHttpResponse;
import webserver.http.Response;

import java.io.IOException;

public class ControllerMapper {
    private final WebpageController webpageController = new WebpageController();

    public void mapping(MyHttpRequest myHttpRequest, MyHttpResponse myHttpResponse) throws IOException {
        HttpMethod method = myHttpRequest.getMethod();
        String path = myHttpRequest.getRequestPath();

        // controller 맵핑
        if ((method.isGet() || method.isPost()) && "/user/create".equals(path)) {
            webpageController.signup(myHttpRequest, myHttpResponse);
            return;
        }
        if (method.isPost() && "/user/login".equals(path)) {
            webpageController.login(myHttpRequest, myHttpResponse);
            return;
        }
        if (method.isGet() && "/user/list.html".equals(path)) {
            webpageController.getUserList(myHttpRequest, myHttpResponse);
            return;
        }
        webpageController.defaultResponse(myHttpRequest, myHttpResponse);
    }
}
