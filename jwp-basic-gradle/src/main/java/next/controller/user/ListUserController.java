package next.controller.user;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import next.dao.UserDao;
import next.dao.UserDaoFactory;
import next.dao.template.DataAccessException;


@Slf4j
public class ListUserController extends AbstractController{
    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) {
      /*  if(!UserUtils.isLoggedIn(request.getSession())){
            return "redirect: /user/loginForm";
        }*/
        UserDao userDao = UserDaoFactory.getUserDao();
        try {
            request.setAttribute("users", userDao.findAll());
        }catch (DataAccessException exception){
            request.setAttribute("users", List.of());
            log.error("{}",exception);
        }
        return this.jspView("/WEB-INF/user/list.jsp");
    }
}
