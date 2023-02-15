package next.controller;

import core.db.DataBase;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import next.dao.UserDao;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class LoginController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Override
    protected String doPost(HttpServletRequest request, HttpServletResponse response) {
        User user = getLoginUser(request.getParameter("userId"), request.getParameter("password"));
        if (user == null) {  // login failed
            return "redirect:/user/login_failed.jsp";
        }

        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        return "redirect:/index.jsp";
    }

    private User getLoginUser(String id, String password) {
        User user = null;
        try {
            user = UserDao.findByUserId(id);
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
