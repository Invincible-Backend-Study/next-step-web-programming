package controller;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.WebpageService;
import webserver.MyHttpRequest;
import webserver.RequestHandler;
import webserver.Response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

public class WebpageController {
    private static final String RESOURCE_PATH = "./webapp";
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private static final WebpageService webpageService = new WebpageService();

    public Response signup(MyHttpRequest myHttpRequest) throws IOException {
        Map<String, String> params = myHttpRequest.getParameters();
        webpageService.signup(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));

        log.debug(DataBase.findAll().toString());

        byte[] body = Files.readAllBytes(new File(RESOURCE_PATH + "/index.html").toPath());
        return new Response("302 Found", "/index.html", null, body);
    }

    public Response login(MyHttpRequest myHttpRequest) throws IOException {
        Map<String, String> params = myHttpRequest.getParameters();
        User user = webpageService.login(params.get("userId"), params.get("password"));

        if (user == null) {
            byte[] body = Files.readAllBytes(new File(RESOURCE_PATH + "/user/login_failed.html").toPath());
            return new Response("404 NotFound", "/user/login_failed.html", null, body);
        }
        byte[] body = Files.readAllBytes(new File(RESOURCE_PATH + "/index.html").toPath());
        return new Response("302 Found", "/index.html", null, body);
    }

    public Response getUserList(MyHttpRequest myHttpRequest) throws IOException {
        Map<String, String> headers = myHttpRequest.getHttpHeaders();

        if ( headers.get("Cookie").equals("logined=true")) {
            String users = webpageService.userListString();
            byte[] body = Files.readAllBytes(new File(RESOURCE_PATH + "/user/list.html").toPath());
            return new Response("200 OK", "/user/list.html", null, body);
        }
        byte[] body = Files.readAllBytes(new File(RESOURCE_PATH + "/index.html").toPath());
        return new Response("200 OK", "/index.html", null, body);
    }
}
