package customwebserver;

import controller.Controller;
import controller.LoginController;
import controller.UserController;
import controller.UserListController;
import customwebserver.http.HttpRequest;
import customwebserver.http.HttpResponse;
import customwebserver.http.session.HttpSession;
import customwebserver.http.session.HttpSessions;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FrontController {
    private static final Logger log = LoggerFactory.getLogger(FrontController.class);
    private static final Map<String, Controller> handlerMapping = new HashMap<>();

    static {
        handlerMapping.put("/user/create", new UserController());
        handlerMapping.put("/user/login", new LoginController());
        handlerMapping.put("/user/list", new UserListController());
    }

    private final HttpRequest httpRequest;
    private final HttpResponse httpResponse;

    public FrontController(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        this.httpRequest = httpRequest;
        this.httpResponse = httpResponse;
    }


    public void doProcess() throws IOException {
        sendRequestDataToResponse();
        requestDispatch();
    }

    /**
     * 응답에 필요한 데이터를 httpRequest에서 전달
     */
    private void sendRequestDataToResponse() {
        if (httpRequest.getCookies().getCookie(HttpSessions.SESSION_ID_NAME) == null) {
            log.debug("generate sessionIdName");
            httpResponse.addCookie(HttpSessions.SESSION_ID_NAME, UUID.randomUUID().toString());
        }
    }

    /**
     * 1. 컨트롤러가 존재하는지 체크 2. 정적 파일 체크
     */
    private void requestDispatch() throws IOException {
        Controller requestController = handlerMapping.get(httpRequest.getPath());
        if (requestController == null) {
            httpResponse.forward(httpRequest.getPath());
            return;
        }
        log.debug("select controller={}", requestController);
        requestController.service(httpRequest, httpResponse);
    }
}
