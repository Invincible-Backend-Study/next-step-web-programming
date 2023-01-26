package controller;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.WebpageService;
import webserver.ResponseHeadersMaker;
import webserver.http.MyHttpRequest;
import webserver.RequestHandler;
import webserver.http.MyHttpResponse;
import webserver.http.Response;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

public class WebpageController {
    private static final String RESOURCE_PATH = "./webapp";
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private static final WebpageService webpageService = new WebpageService();

    public void defaultResponse(MyHttpRequest myHttpRequest, MyHttpResponse myHttpResponse) throws IOException {
        String path = myHttpRequest.getRequestPath();
        myHttpResponse.forward(path);
    }

    public void signup(MyHttpRequest myHttpRequest, MyHttpResponse myHttpResponse) throws IOException {
        Map<String, String> params = myHttpRequest.getAllParameters();
        webpageService.signup(
                myHttpRequest.getHeader("userId"),
                myHttpRequest.getHeader("password"),
                myHttpRequest.getHeader("name"),
                myHttpRequest.getHeader("email")
        );
        log.debug("가입한 유저목록: " + DataBase.findAll().toString());

        myHttpResponse.sendRedirect("/index.html");
    }

    public void login(MyHttpRequest myHttpRequest, MyHttpResponse myHttpResponse) throws IOException {
        User user = webpageService.login(myHttpRequest.getHeader("userId"), myHttpRequest.getHeader("password"));

        if (user == null) {
            myHttpResponse.sendRedirect("/user/login_failed.html");
        }
        myHttpResponse.addHeader("Set-Cookie", "logined=true");
        myHttpResponse.sendRedirect("/index.html");
    }

    public void getUserList(MyHttpRequest myHttpRequest, MyHttpResponse myHttpResponse) throws IOException {
        if ("logined=true".equals(myHttpRequest.getHeader("Cookie"))) {
            String users = webpageService.userListString();
            byte[] body = dynamicUserList(users);
            myHttpResponse.forwardBody(body);
        }
        myHttpResponse.sendRedirect("/user/login.html");
    }

    private static byte[] dynamicUserList(String users) throws IOException {
        // 동적 페이지 생성
        StringBuilder sb = new StringBuilder();
        File file = new File(RESOURCE_PATH + "/user/list.html");
        BufferedReader br = new BufferedReader((new FileReader(file)));
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\r\n");
        }
        String content = new String(sb);
        content = content.replaceAll("noUserInDB", users);

        byte[] body = content.getBytes();
        return body;
    }
}
