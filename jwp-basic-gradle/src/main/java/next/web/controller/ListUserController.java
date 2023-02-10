package next.web.controller;

import core.mvcframework.AbstractController;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.model.User;
import next.web.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ListUserController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(ListUserController.class);

    private final UserService userService = new UserService();

    @Override
    protected String doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<User> users = userService.findAllUsers();
            request.setAttribute("users", users);
            return "user/list";
        } catch (SQLException e) {
            log.error(e.getMessage());
            return "redirect:/";
        }
    }
}
