package next.web.controller.user;

import next.dao.UserDao;
import next.model.User;
import next.mvc.AbstractController;
import next.mvc.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

public class UserLoginController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

    private final UserDao userDao = new UserDao();
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse res) {
        User user = userDao.findByUserId(req.getParameter("userId"));
        if (user != null && Objects.equals(user.getPassword(), req.getParameter("password"))) {
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            return jspView("redirect:/");
        }
        return jspView("/user/login.jsp");
    }
}
