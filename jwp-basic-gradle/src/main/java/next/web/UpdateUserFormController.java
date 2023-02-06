package next.web;

import core.db.DataBase;
import next.model.User;
import next.mvc.Controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class UpdateUserFormController implements Controller {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) {
        User user = DataBase.findUserById(req.getParameter("userId"));
        if (user == null) {
            return "/user/update.jsp";
        }
        if (Objects.equals(user.getPassword(), req.getParameter("password"))) {
            User newUser = new User(
                    req.getParameter("userId"),
                    req.getParameter("password"),
                    req.getParameter("name"),
                    req.getParameter("email"));
            user.update(newUser);
            return "redirect:/";
        }
        return "/user/update.jsp";
    }
}
