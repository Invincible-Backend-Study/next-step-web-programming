package webserver;

import controller.WebpageController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ControllerMapper {
    private static final String RESOURCE_PATH = "./webapp";
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
        if ("GET".equals(method) && "/user/list".equals(path)) {
            return webpageController.getUserList(myHttpRequest);
        }

        // 맵핑할게 없는 경우 해당 path에 파일을 반환
        byte[] body = Files.readAllBytes(new File(RESOURCE_PATH + path).toPath());
        return new Response("200 OK", null, body);
    }
}
