package webserver;

import controller.WebpageController;
import webserver.http.HttpMethod;
import webserver.http.MyHttpRequest;
import webserver.http.Response;

import java.io.IOException;

public class ControllerMapper {
    private final WebpageController webpageController = new WebpageController();

    public Response mapping(MyHttpRequest myHttpRequest) throws IOException {
        HttpMethod method = myHttpRequest.getMethod();
        String path = myHttpRequest.getRequestPath();

        // controller 맵핑
        if ((method.isGet() || method.isPost()) && "/user/create".equals(path)) {
            return webpageController.signup(myHttpRequest);
        }
        if (method.isPost() && "/user/login".equals(path)) {
            return webpageController.login(myHttpRequest);
        }
        if (method.isGet() && "/user/list.html".equals(path)) {
            return webpageController.getUserList(myHttpRequest);
        }
        return webpageController.defaultResponse(myHttpRequest);
    }
}
