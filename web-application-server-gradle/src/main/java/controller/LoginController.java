package controller;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.MyHttpRequest;
import webserver.http.MyHttpResponse;

public class LoginController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Override
    public void service(MyHttpRequest myHttpRequest, MyHttpResponse myHttpResponse) {
        User user = login(myHttpRequest.getParameter("userId"), myHttpRequest.getParameter("password"));
        if (user == null) {
            myHttpResponse.sendRedirect("/user/login_failed.html");
            return;
        }
        myHttpResponse.addHeader("Set-Cookie", "logined=true");
        myHttpResponse.sendRedirect("/index.html");
    }

    private User login(String userId, String password) {
        User user = DataBase.findUserById(userId);
        if (user == null) {
            return null;
        }
        if (user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
