package controller;

import db.DataBase;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;
import webserver.http.MyHttpRequest;
import webserver.http.MyHttpResponse;

public class UserListController extends AbstractController {
    private static final String RESOURCE_PATH = "./webapp";
    private static final Logger log = LoggerFactory.getLogger(UserListController.class);

    @Override
    public void service(MyHttpRequest myHttpRequest, MyHttpResponse myHttpResponse) throws IOException {
        if ("logined=true".equals(myHttpRequest.getHeader("Cookie"))) {
            log.debug("=====> {}", myHttpRequest.getHeader("Cookie"));
            String users = userListString();
            byte[] body = dynamicUserList(users);
            myHttpResponse.forwardBody(body);
            return;
        }
        log.debug("=====> no");
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


    public String userListString() {
        Collection<User> users = DataBase.findAll();
        return userListBuilder(users);
    }

    public String userListBuilder(Collection<User> users) {
        StringBuilder builder = new StringBuilder();
        String result = "";

        int i = 0;
        for (User user : users) {
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
