package next.api.user.controller;

import core.web.ModelAndView;
import next.api.user.service.UserService;
import next.common.controller.AbstractController;
import next.api.user.dao.UserDao;
import next.api.user.model.User;
import next.common.view.JspView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

public class CreateUserController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);
    private final UserService userService = UserService.getInstance();

    @Override
    protected ModelAndView doPost(HttpServletRequest request, HttpServletResponse response) {
        User user = new User(
                request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email")
        );

        try {
            userService.addUser(user);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return new ModelAndView(new JspView("redirect:/user/list"));
    }

}
