package next.web;

import core.db.DataBase;
import next.model.User;
import next.mvc.Controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Objects;

public class UserLoginController implements Controller {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) {
        User user = DataBase.findUserById(req.getParameter("userId"));
        if (user != null && Objects.equals(user.getPassword(), req.getParameter("password"))) {
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            return "redirect:/";
        }
        return "/user/login.jsp";
    }
}
