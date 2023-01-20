package controller;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.WebpageService;
import webserver.ResponseHeadersMaker;
import webserver.http.MyHttpRequest;
import webserver.RequestHandler;
import webserver.http.Response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

public class WebpageController {
    private static final String RESOURCE_PATH = "./webapp";
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private static final WebpageService webpageService = new WebpageService();

    public Response defaultResponse(MyHttpRequest myHttpRequest) throws IOException {
        String path = myHttpRequest.getRequestPath();

        byte[] body = Files.readAllBytes(new File(RESOURCE_PATH + path).toPath());

        List<String> headers = null;
        if (path.endsWith(".css")) {
            headers = ResponseHeadersMaker.css(body.length);
        } else {
            headers = ResponseHeadersMaker.ok(body.length);
        }

        return new Response(headers, body);
    }

    public Response signup(MyHttpRequest myHttpRequest) throws IOException {
        Map<String, String> params = myHttpRequest.getParameters();
        webpageService.signup(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));

        byte[] body = Files.readAllBytes(new File(RESOURCE_PATH + "/index.html").toPath());
        List<String> headers = ResponseHeadersMaker.found("/index.html");

        return new Response(headers, body);
    }

    public Response login(MyHttpRequest myHttpRequest) throws IOException {
        Map<String, String> params = myHttpRequest.getParameters();
        User user = webpageService.login(params.get("userId"), params.get("password"));

        if (user == null) {
            byte[] body = Files.readAllBytes(new File(RESOURCE_PATH + "/user/login_failed.html").toPath());
            List<String> headers = ResponseHeadersMaker.found("/user/login_failed.html");
            return new Response(headers, body);
        }
        byte[] body = Files.readAllBytes(new File(RESOURCE_PATH + "/index.html").toPath());
        List<String> headers = ResponseHeadersMaker.foundLogined("/index.html");
        return new Response(headers, body);
    }

    public Response getUserList(MyHttpRequest myHttpRequest) throws IOException {
        Map<String, String> requestHeaders = myHttpRequest.getHttpHeaders();

        log.debug("----->" + requestHeaders.get("Cookie"));
        if ( requestHeaders.get("Cookie").equals("logined=true")) {
            String users = webpageService.userListString();
            log.debug("user -----> "+users);
            byte[] body = users.getBytes();
            List<String> headers = ResponseHeadersMaker.ok(body.length);
            return new Response(headers, body);
        }
        byte[] body = Files.readAllBytes(new File(RESOURCE_PATH + "/user/login.html").toPath());
        List<String> headers = ResponseHeadersMaker.found("/index.html");
        return new Response(headers, body);
    }
}
