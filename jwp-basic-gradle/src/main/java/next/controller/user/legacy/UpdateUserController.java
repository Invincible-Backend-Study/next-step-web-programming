package next.controller.user.legacy;

import core.mvcframework.ModelAndView;
import core.mvcframework.controller.AbstractController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import next.model.User;
import next.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateUserController extends AbstractController {

    private static final Logger log = LoggerFactory.getLogger(UpdateUserController.class);

    private final UserService userService;

    public UpdateUserController(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public ModelAndView execute(final HttpServletRequest request, final HttpServletResponse response) {
        User updatedUser = new User(
                request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email")
        );
        userService.updateUserInformation(updatedUser);
        log.info("updatedUser={}", updatedUser);
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        session.setAttribute("user", updatedUser);
        return jspView("redirect:/users");
    }

}
