package next.controller.user.legacy.auth;

import core.mvcframework.ModelAndView;
import core.mvcframework.controller.AbstractController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.model.User;
import next.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignUpController extends AbstractController {

    private static final Logger log = LoggerFactory.getLogger(SignUpController.class);

    private final UserService userService;

    public SignUpController(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public ModelAndView execute(final HttpServletRequest request, final HttpServletResponse response) {
        User user = new User(
                request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email")
        );
        userService.signUp(user);
        log.debug("createUser={}", user);
        return jspView("redirect:/");
    }

}
