package service;

import db.DataBase;
import java.util.ArrayList;
import java.util.List;
import model.User;

public class UserService {

    public void addUser(final User user) {
        DataBase.addUser(user);
    }

    public User login(final String userId, final String password) {
        User user = DataBase.findUserById(userId);
        if (user.containPassword(password)) {
            return user;
        }
        return null;
    }

    public List<User> findAll() {
        return new ArrayList<>(DataBase.findAll());
    }
}
