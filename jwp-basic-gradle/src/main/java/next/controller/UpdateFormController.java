package next.controller;

import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import next.dao.UserDao;
import next.dao.UserDaoFactory;


@Slf4j
public class UpdateFormController implements Controller{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        final String userId = request.getParameter("userId");
        UserDao userDao = UserDaoFactory.getUserDao();
        try {
            final var user = userDao.findByUserId(userId);
            request.setAttribute("user", user);
        } catch (SQLException e) {
            log.error("{}",e);
            return "redirect: /user/list";
        }

        return "/WEB-INF/user/update.jsp";
    }
}
