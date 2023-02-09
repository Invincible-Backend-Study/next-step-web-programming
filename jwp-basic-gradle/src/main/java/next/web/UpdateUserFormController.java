package next.web;

import java.sql.SQLException;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.dao.UserDao;
import next.model.User;
import next.mvc.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateUserFormController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) {
        try {
            UserDao userDao = new UserDao();
            User user = userDao.findByUserId(req.getParameter("userId"));

            if (user == null) {
                return "/user/update.jsp";
            }
            if (Objects.equals(user.getPassword(), req.getParameter("password"))) {
                User newUser = new User(
                        req.getParameter("userId"),
                        req.getParameter("password"),
                        req.getParameter("name"),
                        req.getParameter("email"));
                userDao.update(newUser,user.getUserId());

                return "redirect:/";
            }
            return "/user/update.jsp";
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return "redirect:/";
    }
}
