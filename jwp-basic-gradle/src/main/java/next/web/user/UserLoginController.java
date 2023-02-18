package next.web.user;

import java.sql.SQLException;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import next.dao.UserDao;
import next.model.User;
import next.mvc.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserLoginController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) {
        User user = new UserDao().findByUserId(req.getParameter("userId"));
        if (user != null && Objects.equals(user.getPassword(), req.getParameter("password"))) {
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            return "redirect:/";
        }
        return "/user/login.jsp";

    }
}
