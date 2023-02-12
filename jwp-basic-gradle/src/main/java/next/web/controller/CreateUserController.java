package next.web.controller;

import core.mvcframework.AbstractController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.model.User;
import next.web.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateUserController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

    private final UserService userService = new UserService();

    @Override
    protected String doGet(final HttpServletRequest request, final HttpServletResponse response) {
        return "user/form";
    }

    @Override
    protected String doPost(final HttpServletRequest request, final HttpServletResponse response) {
        User user = new User(
                request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email")
        );
        userService.signUp(user);
        log.debug("createUser={}", user);
        return "redirect:/";
    }
}
