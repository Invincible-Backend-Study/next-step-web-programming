package next.api.auth.controller;

import core.web.ModelAndView;
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

public class LoginController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    private final UserDao userDao = UserDao.getInstance();

    @Override
    protected ModelAndView doPost(HttpServletRequest request, HttpServletResponse response) {
        User user = getLoginUser(request.getParameter("userId"), request.getParameter("password"));
        if (user == null) {  // login failed
            return new ModelAndView(new JspView("redirect:/user/login_failed.jsp"));
        }

        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        return new ModelAndView(new JspView("redirect:/index"));
    }

    private User getLoginUser(String id, String password) {
        User user = null;
        try {
            user = userDao.findByUserId(id);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }

        if (user == null) {
            return null;
        }
        if (user.isAuthWith(id, password)) {
            return user;
        }
        return null;
    }
}
