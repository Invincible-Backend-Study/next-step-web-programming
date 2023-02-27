package next.user.controller;

import core.jdbc.DataAccessException;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import next.user.dao.UserDao;
import next.user.dao.UserDaoFactory;


@Slf4j
public class ListUserController extends AbstractController {
    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) {
      /*  if(!UserUtils.isLoggedIn(request.getSession())){
            return "redirect: /user/loginForm";
        }*/
        UserDao userDao = UserDaoFactory.getUserDao();
        try {
            request.setAttribute("users", userDao.findAll());
        } catch (DataAccessException exception) {
            request.setAttribute("users", List.of());
            log.error("{}", exception);
        }
        return this.jspView("/WEB-INF/user/list.jsp");
    }
}
