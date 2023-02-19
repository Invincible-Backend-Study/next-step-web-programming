package next.web.controller.user;

import next.dao.UserDao;
import next.mvc.AbstractController;
import next.mvc.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserListController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

    private final UserDao userDao = new UserDao();
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse res) {
        req.setAttribute("users", userDao.findAllUser());
        ModelAndView mav = jspView("/user/list.jsp");
        mav.addObject("users",userDao.findAllUser());
        return mav;
    }
}
