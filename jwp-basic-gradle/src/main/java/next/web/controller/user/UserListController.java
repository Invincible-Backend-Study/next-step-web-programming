package next.web.controller.user;

import next.dao.UserDao;
import next.mvc.AbstractController;
import next.mvc.ModelAndView;
import next.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserListController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

    private final UserService userService = new UserService();

    public ModelAndView execute(HttpServletRequest req, HttpServletResponse res) {
        return jspView("/user/list.jsp").addObject("users", userService.findAllUser());
    }
}
