package next.api.user.controller;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.web.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import next.api.user.service.UserService;
import next.common.controller.AbstractController;
import next.api.user.dao.UserDao;
import next.api.user.model.User;
import next.common.view.JspView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

@Controller
public class UpdateUserController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(UpdateUserController.class);
    private final UserService userService = UserService.getInstance();

    @Override
    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    protected ModelAndView doPost(HttpServletRequest request, HttpServletResponse response) {
        User user = new User(
                request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email"));

        userService.updateUser(user);

        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        return new ModelAndView(new JspView("redirect:/user/list"));
    }


}
