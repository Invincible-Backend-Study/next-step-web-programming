package webserver;

import controller.WebpageController;
import webserver.http.MyHttpRequest;
import webserver.http.Response;

import java.io.IOException;

public class ControllerMapper {
    private final WebpageController webpageController = new WebpageController();

    public Response mapping(MyHttpRequest myHttpRequest) throws IOException {
        String method = myHttpRequest.getHttpMethod();
        String path = myHttpRequest.getRequestPath();

        // controller 맵핑
        if (("GET".equals(method) || "POST".equals(method)) && "/user/create".equals(path)) {
            return webpageController.signup(myHttpRequest);
        }
        if ("POST".equals(method) && "/user/login".equals(path)) {
            return webpageController.login(myHttpRequest);
        }
        if ("GET".equals(method) && "/user/list.html".equals(path)) {
            return webpageController.getUserList(myHttpRequest);
        }
        return webpageController.defaultResponse(myHttpRequest);
    }
}
