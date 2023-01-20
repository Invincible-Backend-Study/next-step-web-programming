package controller;

import controller.http.HttpRequest;
import controller.http.HttpResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.enums.HttpMethod;

public class FrontController {
    private static final Logger log = LoggerFactory.getLogger(FrontController.class);
    private static final Map<String, Object> handlerMapping = new HashMap<>();

    static {
        handlerMapping.put("/user/create", new UserController());
    }

    private final HttpRequest httpRequest;
    private final HttpResponse httpResponse;

    public FrontController(final HttpRequest httpRequest, final HttpResponse httpResponse) throws IOException {
        this.httpRequest = httpRequest;
        this.httpResponse = httpResponse;
        requestDispatch();
    }

    /**
     * 1. 컨트롤러가 존재하는지 체크
     * 2. 정적 파일 체크
     */
    private void requestDispatch() throws IOException {
        Controller requestController = (Controller) handlerMapping.get(httpRequest.getRequestUri());
        if (requestController == null) {
            byte[] fileBytes = Files.readAllBytes(new File("./webapp" + httpRequest.getRequestUri()).toPath());
            httpResponse.successStaticUri(fileBytes);
            return;
        }
        if (httpRequest.containMethod(HttpMethod.GET)) {
            boolean success = requestController.doGet(httpRequest, httpResponse);
            response(success);
        }
    }

    private void response(final boolean success) {
        if (success) {
            httpResponse.successMappingUri();
        }
    }

}
