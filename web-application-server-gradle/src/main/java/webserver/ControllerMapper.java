package webserver;

import controller.WebpageController;

import java.io.File;
import java.nio.file.Files;

public class ControllerMapper {
    private final WebpageController webpageController = new WebpageController();

    public String mapping(MyHttpRequest myHttpRequest) {
        String method = myHttpRequest.getHttpMethod();
        String path = myHttpRequest.getRequestPath();

        // controller 맵핑
        if ("GET".equals(method) && "/user/create".equals(path)) {
            return webpageController.signup(myHttpRequest);
        }
        return null;
    }
}
