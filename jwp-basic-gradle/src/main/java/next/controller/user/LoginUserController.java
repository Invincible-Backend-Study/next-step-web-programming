package next.controller.user;

import core.mvcframework.controller.Controller;
import core.mvcframework.view.JspView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import next.model.User;
import next.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginUserController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(LoginUserController.class);

    private final UserService userService = new UserService();

    @Override
    public JspView execute(final HttpServletRequest request, final HttpServletResponse response) {
        HttpSession session = request.getSession();
        try {
            User loginedUser = userService.loginUser(request.getParameter("userId"), request.getParameter("password"));
            if (loginedUser == null) {
                return new JspView("redirect:/users/loginFailed");
            }
            session.setAttribute("user", loginedUser);
            return new JspView("redirect:/");
        } catch (IllegalArgumentException exception) {
            log.error(exception.getMessage());
            return new JspView("redirect:/users/loginFailed");
        }
    }
}
