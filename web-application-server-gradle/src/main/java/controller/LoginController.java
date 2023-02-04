package controller;

import customwebserver.http.HttpRequest;
import customwebserver.http.HttpResponse;
import customwebserver.http.session.HttpSession;
import java.io.IOException;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;

public class LoginController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    private final UserService userService = new UserService();

    @Override
    public void doPost(final HttpRequest httpRequest, final HttpResponse httpResponse) throws IOException {
        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");
        if (userId == null || password == null) {
            throw new IllegalArgumentException("[ERROR] 로그인 정보를 모두 입력해야 합니다.");
        }
        User user = userService.login(userId, password);
        if (user == null) {
            httpResponse.sendRedirect("/user/login_failed.html");
            return;
        }
        // 세션 적용
        HttpSession session = httpRequest.getSession();
        session.setAttribute("user", user);
        log.debug("login user={}", user);
        httpResponse.sendRedirect("/index.html");
    }
}
