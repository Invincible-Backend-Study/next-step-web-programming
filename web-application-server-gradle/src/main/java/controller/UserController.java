package controller;

import customwebserver.http.HttpRequest;
import customwebserver.http.HttpResponse;
import java.io.IOException;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;

public class UserController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService = new UserService();

    @Override
    public boolean doGet(final HttpRequest httpRequest, final HttpResponse httpResponse) throws IOException {
        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");
        String name = httpRequest.getParameter("name");
        String email = httpRequest.getParameter("email");
        if (userId == null || password == null || name == null || email == null) {
            throw new IllegalArgumentException("[ERROR] GET 회원가입 오류");
        }
        User user = new User(userId, password, name, email);
        log.debug("GET 회원가입 user={}", user);
        userService.addUser(user);
        httpResponse.sendRedirect("/index.html");
        return true;
    }

    @Override
    public boolean doPost(final HttpRequest httpRequest, final HttpResponse httpResponse) throws IOException {
        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");
        String name = httpRequest.getParameter("name");
        String email = httpRequest.getParameter("email");
        if (userId == null || password == null || name == null || email == null) {
            throw new IllegalArgumentException("[ERROR] POST 회원가입 오류");
        }
        User user = new User(userId, password, name, email);
        log.debug("POST 회원가입 user={}", user);
        userService.addUser(user);
        httpResponse.sendRedirect("/index.html");
        return true;
    }
}
