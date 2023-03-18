package next.controller.user.legacy.auth;

import core.mvcframework.ModelAndView;
import core.mvcframework.controller.AbstractController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import next.model.User;
import next.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginController extends AbstractController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    private final UserService userService;

    public LoginController(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public ModelAndView execute(final HttpServletRequest request, final HttpServletResponse response) {
        HttpSession session = request.getSession();
        try {
            User loginedUser = userService.loginUser(request.getParameter("userId"), request.getParameter("password"));
            if (loginedUser == null) {
                return jspView("redirect:/loginFailed");
            }
            session.setAttribute("user", loginedUser);
            return jspView("redirect:/");
        } catch (IllegalArgumentException exception) {
            log.error(exception.getMessage());
            return jspView("redirect:/loginFailed");
        }
    }

}
