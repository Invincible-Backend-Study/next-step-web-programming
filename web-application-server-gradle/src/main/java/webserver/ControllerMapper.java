package webserver;

import controller.WebpageController;

public class ControllerMapper {
    private final WebpageController webpageController = new WebpageController();

    public Response mapping(MyHttpRequest myHttpRequest) {
        String method = myHttpRequest.getHttpMethod();
        String path = myHttpRequest.getRequestPath();

        // controller 맵핑
        if (("GET".equals(method) || "POST".equals(method)) && "/user/create".equals(path)) {
            return webpageController.signup(myHttpRequest);
        }
        if ("POST".equals(method) && "/user/login".equals(path)) {
            return webpageController.login(myHttpRequest);
        }
        if ("GET".equals(method) && "/user/list".equals(path)) {
            return webpageController.getUserList(myHttpRequest);
        }
        return new Response("200 OK", path, null, null);
    }
}
