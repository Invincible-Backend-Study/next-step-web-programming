package next.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.model.User;

import next.mvc.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.db.DataBase;

public class CreateUserController implements Controller {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) {
        User user = new User(
                req.getParameter("userId"),
                req.getParameter("password"),
                req.getParameter("name"),
                req.getParameter("email")
        );
        try {
            user.validationLogin();
        } catch (IllegalStateException e) {
            log.debug("공백 : { }", e);
        }
        log.debug("user : {}", user.getUserId());
        DataBase.addUser(user);
        return "/user/list.jsp";
    }
}
