package next.web.controller.user;

import next.dao.UserDao;
import next.model.User;
import next.mvc.AbstractController;
import next.mvc.ModelAndView;
import next.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class UpdateUserFormController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

    private final UserService userService = new UserService();

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse res) {
        User user = userService.findByUserId(req.getParameter("userId"));
        if (user == null) {
            return jspView("/user/update.jsp");
        }
        if (Objects.equals(user.getPassword(), req.getParameter("password"))) {
            User newUser = new User(
                    req.getParameter("userId"),
                    req.getParameter("password"),
                    req.getParameter("name"),
                    req.getParameter("email"));
            userService.updateUser(newUser, user.getUserId());
            return jspView("redirect:/");
        }
        return jspView("/user/update.jsp");

    }
}
