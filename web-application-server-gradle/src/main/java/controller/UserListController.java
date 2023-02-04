package controller;

import customwebserver.http.HttpRequest;
import customwebserver.http.HttpResponse;
import customwebserver.http.session.HttpSession;
import java.io.IOException;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;

public class UserListController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(UserListController.class);
    private final UserService userService = new UserService();

    @Override
    public void doGet(final HttpRequest httpRequest, final HttpResponse httpResponse) throws IOException {
        HttpSession session = httpRequest.getSession();
        User user = (User) session.getAttribute("user");
        log.debug("find from session user={}", user);
        if (user == null) {
            httpResponse.sendRedirect("/user/login.html");
            return;
        }
        httpResponse.sendResponseBody(user.toString());
    }
}
