package next.controller.user;

import core.mvcframework.ModelAndView;
import core.mvcframework.controller.AbstractController;
import core.mvcframework.controller.Controller;
import core.mvcframework.view.JspView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.model.User;
import next.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateUserController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

    private final UserService userService = new UserService();

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
        return jspView("redirect/");
    }
}
