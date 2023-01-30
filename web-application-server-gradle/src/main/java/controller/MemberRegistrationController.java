package controller;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import http.parser.HttpBodyParser;
import javax.xml.crypto.Data;
import model.User;
import util.HttpRequestUtils;
import util.method.HttpMethod;

import java.io.IOException;
import java.util.Map;

public class MemberRegistrationController implements Controller {

    @Override
    public void use(final HttpRequest httpRequest, final HttpResponse httpResponse) throws IOException {
        saveUserData(httpParseMethod(httpRequest));
        httpResponse.loginSucess();
    }

    private static String httpParseMethod(final HttpRequest httpRequest) throws IOException {
        HttpMethod requestMethod = HttpMethod.from(httpRequest.getMethod());
        HttpBodyParser httpMethodParser = requestMethod.confirmMethod();
        return httpMethodParser.parseBody(httpRequest);
    }

    private static void saveUserData(final String bodyData) {
        Map<String, String> queryString = HttpRequestUtils.parseQueryString(bodyData);
        User user = new User(queryString.get("userId"), queryString.get("password"), queryString.get("name"),
                queryString.get("email"));
        DataBase.addUser(user);
    }

}
