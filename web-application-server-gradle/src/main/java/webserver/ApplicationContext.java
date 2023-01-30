package webserver;

import common.PreviousMappingController;
import http.request.HttpRequest;
import http.response.HttpResponse;
import java.io.IOException;

public class ApplicationContext {
    private static final PreviousMappingController mappingController = new PreviousMappingController();

    public static void process(HttpRequest request, HttpResponse response) throws IOException {
        // request interceptor 필요한 경우

        mappingController.findControllerBy(request.getRequestPath())
                .orElseGet(mappingController::getResourceHandleController)// handle error or html file
                .process(request, response);

        // response interceptor 필요한 경우

        response.send();
    }


}
