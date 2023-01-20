package controller;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.WebpageService;
import webserver.MyHttpRequest;
import webserver.RequestHandler;
import webserver.Response;

import java.util.Map;

public class WebpageController {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private static final WebpageService webpageService = new WebpageService();

    public Response signup(MyHttpRequest myHttpRequest) {
        Map<String, String> params = myHttpRequest.getParameters();
        webpageService.signup(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));

        log.debug(DataBase.findAll().toString());
        return new Response("302 Found", "/index.html", null, null);
    }

    public Response login(MyHttpRequest myHttpRequest) {
        Map<String, String> params = myHttpRequest.getParameters();
        User user = webpageService.login(params.get("userId"), params.get("password"));

        if (user == null) {
            return new Response("404 NotFound", "/user/login_failed.html", null, null);
        }
        return new Response("302 Found", "/index.html", null, null);
    }

    public Response getUserList(MyHttpRequest myHttpRequest) {
        Map<String, String> headers = myHttpRequest.getHttpHeaders();

        if ( headers.get("Cookie").equals("logined=true")) {
            String users = webpageService.userListString();
            return new Response("200 OK", "/user/list.html", null, null);  // 기존 null
        }
        return new Response("200 OK", "/index.html", null, null);
    }
}
