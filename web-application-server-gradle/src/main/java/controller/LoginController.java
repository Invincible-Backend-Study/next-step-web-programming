package controller;

import customwebserver.http.HttpRequest;
import customwebserver.http.HttpResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;

public class LoginController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    private final UserService userService = new UserService();

    @Override
    protected void doGet(final HttpRequest httpRequest, final HttpResponse httpResponse) {
    }

    @Override
    protected void doPost(final HttpRequest httpRequest, final HttpResponse httpResponse) throws IOException {
        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");
        if (userId == null || password == null) {
            throw new IllegalArgumentException("[ERROR] 로그인 정보를 모두 입력해야 합니다.");
        }
        Boolean login = userService.login(userId, password);
        log.debug("login result={}", login);
        httpResponse.addCookie("logined", login);
        if (login) {
            httpResponse.sendRedirect("/index.html");
            return;
        }
        httpResponse.sendRedirect("/user/login_failed.html");
    }
}
