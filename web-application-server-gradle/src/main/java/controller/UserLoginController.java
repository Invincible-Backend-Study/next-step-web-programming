package controller;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import model.User;
import util.HttpRequestUtils;

import java.io.IOException;
import java.util.Map;

public class UserLoginController implements Controller {
    @Override
    public void use(final HttpRequest httpRequest, final HttpResponse httpResponse) throws IOException {
        Map<String, String> queryString = HttpRequestUtils.parseQueryString(httpRequest.parseHttpRequestBody());
        User user = DataBase.findUserById(queryString.get("userId"));
        judgementLogin(httpResponse, queryString, user);
    }

    private static void judgementLogin(HttpResponse httpResponse, Map<String, String> queryString, User user) {
        if (user != null && user.getPassword().equals(queryString.get("password"))) {
            httpResponse.loginSucess();
            return;
        }
        httpResponse.loginFailed();
    }
}
