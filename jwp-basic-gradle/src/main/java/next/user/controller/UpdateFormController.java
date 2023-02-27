package next.user.controller;

import core.jdbc.DataAccessException;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import next.user.dao.UserDao;
import next.user.dao.UserDaoFactory;


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
            log.error("{}", e);
            return this.jspView("redirect: /user/list");
        }
        return this.jspView("/WEB-INF/user/update.jsp");
    }
}
