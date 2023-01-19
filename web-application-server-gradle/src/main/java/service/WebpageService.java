package service;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

public class WebpageService {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    public void signup(String userId, String password, String name, String email) {
        User user = new User(userId, password, name, email);
        DataBase.addUser(user);
    }

    public User login(String userId, String password) {
        User user = DataBase.findUserById(userId);
        if (user != null) {
            log.debug("USER_INFROM: " + user.toString() + " password:" + user.getPassword() + " ");
        }
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
