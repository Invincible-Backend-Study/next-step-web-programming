package next.controller;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import next.dao.UserDao;
import next.dao.UserDaoFactory;
import next.dao.template.DataAccessException;


@Slf4j
public class UpdateFormController extends AbstractController {
    private final UserDao userDao = UserDaoFactory.getUserDao();
    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) {
        final String userId = request.getParameter("userId");
        try {
            final var user = userDao.findByUserId(userId);
            request.setAttribute("user", user);
        } catch (DataAccessException e) {
            log.error("{}",e);
            return this.jspView("redirect: /user/list");
        }
        return this.jspView("/WEB-INF/user/update.jsp");
    }
}
