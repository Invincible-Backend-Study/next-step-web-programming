package controller;

import controller.AbstractController;
import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;
import webserver.http.MyHttpRequest;
import webserver.http.MyHttpResponse;
import webserver.http.Response;

public class CreateUserController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    @Override
    public void service(MyHttpRequest myHttpRequest, MyHttpResponse myHttpResponse) {
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
    public Response doGet(MyHttpRequest myHttpRequest) {
        return null;
    }

    @Override
    public Response doPost(MyHttpRequest myHttpRequest) {
        return null;
    }
}
