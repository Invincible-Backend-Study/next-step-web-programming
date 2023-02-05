package next.web.controller;

import core.web.controller.AbstractController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import next.model.User;
import next.web.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginUserController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(LoginUserController.class);

    private final UserService userService = new UserService();

    @Override
    protected String doGet(final HttpServletRequest request, final HttpServletResponse response) {
        HttpSession session = request.getSession();
        Object loginTry = session.getAttribute("loginTry");
        if (loginTry != null && (boolean) loginTry) {
            session.removeAttribute("loginTry");
            return "user/login_failed";
        }
        return "user/login";
    }

    @Override
    protected String doPost(final HttpServletRequest request, final HttpServletResponse response) {
        HttpSession session = request.getSession();
        try {
            User loginedUser = userService.loginUser(request.getParameter("userId"), request.getParameter("password"));
            if (loginedUser == null) {
                session.setAttribute("loginTry", true);
                return "redirect:/user/login";
            }
            session.setAttribute("user", loginedUser);
            return "redirect:/";
        } catch (IllegalArgumentException exception) {
            log.error(exception.getMessage());
            session.setAttribute("loginTry", true);
            return "redirect:/user/login";
        }
    }
}
