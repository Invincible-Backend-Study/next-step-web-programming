package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.web.View;
import next.dao.UserDao;
import next.model.User;
import next.view.JspView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class UpdateUserController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(UpdateUserController.class);

    @Override
    protected View doPost(HttpServletRequest request, HttpServletResponse response) {
        User user = new User(
                request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email"));
        log.debug("updateUser : {}", user);
        try {
            UserDao.update(user);
        } catch (SQLException e) {
            log.error(e.toString());
        }

        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        return new JspView("redirect:/user/list");
    }
}
