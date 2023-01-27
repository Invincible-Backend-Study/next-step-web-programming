package controller;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.MyHttpRequest;
import webserver.http.MyHttpResponse;

public class CreateUserController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

    @Override
    public void doGet(MyHttpRequest myHttpRequest, MyHttpResponse myHttpResponse) {
        User user = new User(
                myHttpRequest.getParameter("userId"),
                myHttpRequest.getParameter("password"),
                myHttpRequest.getParameter("name"),
                myHttpRequest.getParameter("email")
        );
        log.debug("user: {}", user);
        DataBase.addUser(user);
        myHttpResponse.sendRedirect("/index.html");
    }

    @Override
    public void doPost(MyHttpRequest myHttpRequest, MyHttpResponse myHttpResponse) {
        User user = new User(
                myHttpRequest.getParameter("userId"),
                myHttpRequest.getParameter("password"),
                myHttpRequest.getParameter("name"),
                myHttpRequest.getParameter("email")
        );
        log.debug("user: {}", user);
        DataBase.addUser(user);
        myHttpResponse.sendRedirect("/index.html");
    }
}
