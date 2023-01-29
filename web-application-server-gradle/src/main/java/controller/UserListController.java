package controller;

import customwebserver.http.HttpRequest;
import customwebserver.http.HttpResponse;
import java.io.IOException;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;

public class UserListController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(UserListController.class);
    private final UserService userService = new UserService();

    @Override
    public void doGet(final HttpRequest httpRequest, final HttpResponse httpResponse) throws IOException {
        Boolean logined = Boolean.valueOf(httpRequest.getCookie("logined"));
        if (!logined) {
            httpResponse.sendRedirect("/user/login.html");
            return;
        }
        StringBuilder userList = new StringBuilder();
        userService.findAll()
                .stream()
                .map(User::toString)
                .forEach(userInfo -> userList.append(userInfo).append("\n"));
        httpResponse.sendResponseBody(userList.toString());
    }

    @Override
    public void doPost(final HttpRequest httpRequest, final HttpResponse httpResponse) throws IOException {
    }
}
