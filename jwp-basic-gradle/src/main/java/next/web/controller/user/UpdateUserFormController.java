package next.web.controller.user;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.UserDao;
import next.model.User;
import next.mvc.AbstractController;
import next.mvc.Controller;
import next.mvc.JspView;
import next.mvc.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateUserFormController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse res) {
        UserDao userDao = new UserDao();
        User user = userDao.findByUserId(req.getParameter("userId"));
        if (user == null) {
            return jspView("/user/update.jsp");
        }
        if (Objects.equals(user.getPassword(), req.getParameter("password"))) {
            User newUser = new User(
                    req.getParameter("userId"),
                    req.getParameter("password"),
                    req.getParameter("name"),
                    req.getParameter("email"));
            userDao.updateUser(newUser, user.getUserId());
            return jspView("redirect:/");
        }
        return jspView("/user/update.jsp");

    }
}
