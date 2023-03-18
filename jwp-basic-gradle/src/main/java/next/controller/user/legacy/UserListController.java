package next.controller.user.legacy;

import core.mvcframework.ModelAndView;
import core.mvcframework.controller.AbstractController;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.model.User;
import next.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserListController extends AbstractController {

    private static final Logger log = LoggerFactory.getLogger(UserListController.class);

    private final UserService userService;

    public UserListController(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public ModelAndView execute(final HttpServletRequest request, final HttpServletResponse response) {
        List<User> users = userService.findAllUsers();
        log.debug("allUserList={}", users);
        request.setAttribute("users", users);
        return jspView("user/list");
    }

}
