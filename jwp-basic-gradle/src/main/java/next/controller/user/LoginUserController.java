package next.controller.user;

import core.mvcframework.ModelAndView;
import core.mvcframework.controller.AbstractController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import next.model.User;
import next.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginUserController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(LoginUserController.class);

    private final UserService userService = new UserService();

    @Override
    public ModelAndView execute(final HttpServletRequest request, final HttpServletResponse response) {
        HttpSession session = request.getSession();
        try {
            User loginedUser = userService.loginUser(request.getParameter("userId"), request.getParameter("password"));
            if (loginedUser == null) {
                return jspView("redirect:/users/loginFailed");
            }
            session.setAttribute("user", loginedUser);
            return jspView("redirect:/");
        } catch (IllegalArgumentException exception) {
            log.error(exception.getMessage());
            return jspView("redirect:/users/loginFailed");
        }
    }
}
