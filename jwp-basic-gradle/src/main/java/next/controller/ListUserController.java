package next.controller;

import core.mvcframework.Controller;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.model.User;
import next.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ListUserController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(ListUserController.class);

    private final UserService userService = new UserService();

    @Override
    public String execute(final HttpServletRequest request, final HttpServletResponse response) {
        List<User> users = userService.findAllUsers();
        log.debug("allUserList={}", users);
        request.setAttribute("users", users);
        return "user/list";
    }
}
