package next.controller.user;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import next.dao.UserDao;
import next.dao.UserDaoFactory;
import next.dao.template.DataAccessException;
import next.model.User;

@Slf4j
public class CreateUserController extends AbstractController {
    private final UserDao userDao = UserDaoFactory.getUserDao();
    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) {

        User user = User.of(
                request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email")
        );
        try {
            userDao.insert(user);
        }catch (DataAccessException exception){
            log.error(exception.getMessage());
            return this.jspView("/WEB-INF/user/signup_failed.jsp");
        }
        return this.jspView("redirect: /user/list");
    }
}
