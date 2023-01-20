package service;

import db.DataBase;
import model.User;

public class UserService {

    public void addUser(final User user) {
        DataBase.addUser(user);
    }

    public boolean login(final String userId, final String password) {
        User user = DataBase.findUserById(userId);
        if (user == null) {
            return false;
        }
        return user.getPassword().equals(password);
    }
}
