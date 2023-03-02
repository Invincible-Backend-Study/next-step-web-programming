package next.api.user.controller;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.web.ModelAndView;
import next.api.user.model.User;
import next.api.user.service.UserService;
import next.common.controller.AbstractController;
import next.common.view.JspView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class CreateUserController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);
    private final UserService userService = UserService.getInstance();

    @Override
    @RequestMapping(value = "/user/create", method = RequestMethod.POST)
    protected ModelAndView doPost(HttpServletRequest request, HttpServletResponse response) {
        User user = new User(
                request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email")
        );

        userService.addUser(user);
        return new ModelAndView(new JspView("redirect:/user/list"));
    }

}
