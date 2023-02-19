package next.controller;

import core.db.DataBase;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import next.dao.UserDao;
import next.dao.UserDaoFactory;
import next.dao.template.DataAccessException;
import next.model.User;

public class UserLoginController extends AbstractController{
    private final UserDao userDao = UserDaoFactory.getUserDao();
    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) {
        final var userId = request.getParameter("userId");
        try {
            final User user = userDao.findByUserId(userId);
            if(user == null){
                return this.jspView("/WEB-INF/user/login_failed.jsp");
            }
            if(user.comparePassword(request.getParameter("password"))){
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                return this.jspView("redirect: /");
            }
            return this.jspView("/WEB-INF/user/login_failed.jsp");
        } catch (DataAccessException e) {
            return this.jspView("/WEB-INF/user/login_failed.jsp");
        }

    }
}
