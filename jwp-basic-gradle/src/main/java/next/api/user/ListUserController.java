package next.api.user;

import core.web.ModelAndView;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import next.common.controller.AbstractController;
import next.api.user.dao.UserDao;
import next.api.user.model.User;
import next.common.view.JspView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class ListUserController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(ListUserController.class);
    private final UserDao userDao = new UserDao();

    @Override
    protected ModelAndView doGet(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Object value = session.getAttribute("user");
        if (value == null) {
            log.debug("Only logged-in users can access.");
            return new ModelAndView(new JspView("redirect:/user/login.jsp"));
        }

        List<User> users = null;
        try {
            users = userDao.findAll();
            request.setAttribute("users", users);
        } catch (SQLException e) {
            log.error(e.toString());
        }
        return new ModelAndView(new JspView("/user/list.jsp")).addModel("users", users);
    }
}
