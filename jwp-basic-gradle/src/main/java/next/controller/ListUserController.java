package next.controller;

import core.db.DataBase;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.web.View;
import next.dao.UserDao;
import next.view.JspView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class ListUserController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(ListUserController.class);

    @Override
    protected View doGet(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Object value = session.getAttribute("user");
        if (value == null) {
            log.debug("Only logged-in users can access.");
            return new JspView("redirect:/user/login.jsp");
        }

        try {
            request.setAttribute("users", UserDao.findAll());
        } catch (SQLException e) {
            log.error(e.toString());
        }
        return new JspView("/user/list.jsp");
    }
}
