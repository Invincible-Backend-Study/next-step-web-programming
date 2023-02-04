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

    /**
     * 현재 세션에서 발생될 수 있는 문제점
     * FrontController에서 response에 MYJSESSIONID를 정의하고 로그인 요청을 즉시하게 되면(GET) 아직
     * Set-Cookie -> Cookie로 request에 Cookie값이 전달되지 않았다. 따라서 request.getSession()시 내부적으로 request헤더에서 MYJSESSIONID로 key값이 된 쿠키값을 찾지만
     * 찾을 수 없어 null이 반환된다.
     * 다음 요청부터는 FrontController에서 정의한 MYJSESSIONID값이 Set-Cookie를 통해 전달됐으므로 Cookie값에 포함된다. 따라서 user/list를 요청하면 로그인을 했지만 접근할 수 없게된다.
     */
    @Override
    protected void doGet(final HttpRequest request, final HttpResponse response) throws IOException {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        log.debug(userId, password);
        request.getSession().setAttribute("user", userService.login(userId, password));
        response.sendRedirect("/index.html");
    }

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
