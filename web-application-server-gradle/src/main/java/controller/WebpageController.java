package controller;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.WebpageService;
import webserver.MyHttpRequest;
import webserver.RequestHandler;

import java.util.Map;

public class WebpageController {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private static final WebpageService webpageService = new WebpageService();

    public String signup(MyHttpRequest myHttpRequest) {
        Map<String, String> params = myHttpRequest.getParameters();
        webpageService.signup(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));

        log.debug(DataBase.findAll().toString());
        return "/index.html";
    }
}
