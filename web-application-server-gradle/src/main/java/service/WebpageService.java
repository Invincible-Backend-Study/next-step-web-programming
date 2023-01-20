package service;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.util.Collection;

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

    public String userListString() {
        Collection<User> users = DataBase.findAll();
        return userListBuilder(users);
    }

    public String userListBuilder(Collection<User> users) {
        StringBuilder builder = new StringBuilder();
        String result = "";

        int i = 0;
        for(User user : users) {
            i++;
            builder.append(result)
                    .append("<tr>")
                    .append("<th scope=\"row\">").append(i).append("</th>")
                    .append("<td>").append(user.getUserId()).append("</td>")
                    .append("<td>").append(user.getName()).append("</td>")
                    .append("<td>").append(user.getEmail()).append("</td>")
                    .append("<td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>")
                    .append("</tr>");
        }

        result = builder.toString();
        return result;
    }
}
