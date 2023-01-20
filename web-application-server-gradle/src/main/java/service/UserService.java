package service;

import db.DataBase;
import model.User;

public class UserService {

    public void addUser(final User user) {
        DataBase.addUser(user);
    }
}
