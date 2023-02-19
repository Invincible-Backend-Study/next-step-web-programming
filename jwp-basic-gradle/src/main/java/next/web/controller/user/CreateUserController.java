package next.web.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.UserDao;
import next.model.User;
import next.mvc.AbstractController;
import next.mvc.JspView;
import next.mvc.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class CreateUserController extends AbstractController {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse res) {
        User user = new User(
                req.getParameter("userId"),
                req.getParameter("password"),
                req.getParameter("name"),
                req.getParameter("email")
        );
        UserDao userDao = new UserDao();
        userDao.addUser(user);
        return jspView("redirect:/");
    }
}
