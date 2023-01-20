package controller;

import controller.http.HttpRequest;
import controller.http.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Override
    public boolean doGet(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");
        String name = httpRequest.getParameter("name");
        String email = httpRequest.getParameter("email");
        if (userId == null || password == null || name == null || email == null) {
            throw new IllegalArgumentException("[ERROR] 회원가입 오류");
        }
        User user = new User(userId, password, name, email);
        log.info("회원가입 user={}", user);

        return true;
    }

    @Override
    public boolean doPost(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        return false;
    }
}
