package controller;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import webserver.RequestHandler;

import java.io.IOException;
import java.util.Collection;

public class UserListController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    @Override
    public void use(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        String loginValue = HttpRequestUtils.parseCookies(httpRequest.getHeaders("Cookie")).get("logined");
        boolean isLogin = Boolean.parseBoolean(loginValue);
        if (isLogin) {
            Collection<User> users = DataBase.findAll();
            users.forEach(user -> log.debug(user.getName()));
            httpResponse.redirectHome();
            return;
        }
        log.debug("You must log in first..");
        httpResponse.redirectHome();
    }
}
