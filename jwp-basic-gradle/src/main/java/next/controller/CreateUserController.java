package next.controller;

import core.db.DataBase;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import next.model.User;

@Slf4j
public class CreateUserController implements Controller {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        if(DataBase.findUserById(request.getParameter("userId")) != null){
            return "/WEB-INF/user/signup_failed.jsp";
        }

        User user = User.of(
                request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email")
        );

        log.info("{}", DataBase.findAll());


        log.debug("user : {}", user);
        DataBase.addUser(user);
        return "redirect: /user/list";
    }
}
