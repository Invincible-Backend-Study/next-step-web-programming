package service;

import db.DataBase;
import model.User;

public class WebpageService {

    public void signup(String userId, String password, String name, String email) {
        User user = new User(userId, password, name, email);
        DataBase.addUser(user);
    }
}
