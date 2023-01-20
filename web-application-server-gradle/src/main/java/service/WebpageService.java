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
            result = builder.append(result)
                    .append("<tr>")
                    .append("<th scope=\"row\">").append(i).append("</th>")
                    .append("<td>").append(user.getUserId()).append("</td>")
                    .append("<td>").append(user.getName()).append("</td>")
                    .append("<td>").append(user.getEmail()).append("</td>")
                    .append("<td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>")
                    .append("</tr>")
                    .toString();
        }
        return result;
    }

//      <table class="table table-hover">
//          <thead>
//            <tr>
//                <th>#</th> <th>사용자 아이디</th> <th>이름</th> <th>이메일</th><th></th>
//            </tr>
//          </thead>
//          <tbody>
//            <tr>
//                <th scope="row">1</th> <td>javajigi</td> <td>자바지기</td> <td>javajigi@sample.net</td><td><a href="#" class="btn btn-success" role="button">수정</a></td>
//            </tr>
//            <tr>
//                <th scope="row">2</th> <td>slipp</td> <td>슬립</td> <td>slipp@sample.net</td><td><a href="#" class="btn btn-success" role="button">수정</a></td>
//            </tr>
//          </tbody>
//      </table>
}
