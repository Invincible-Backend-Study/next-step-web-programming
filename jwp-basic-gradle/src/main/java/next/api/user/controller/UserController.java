package next.api.user.controller;

import core.annotation.Controller;
import core.annotation.Inject;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.web.ModelAndView;
import next.api.user.model.User;
import next.api.user.service.UserService;
import next.common.model.Result;
import next.common.view.JsonView;
import next.common.view.JspView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    @Inject
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ModelAndView userSignUp(HttpServletRequest request, HttpServletResponse response) {
        User user = new User(
                request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email")
        );

        userService.addUser(user);
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        return new ModelAndView(new JspView("redirect:/users"));
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView userList(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Object value = session.getAttribute("user");
        if (value == null) {
            log.debug("Only logged-in users can access.");
            return new ModelAndView(new JspView("redirect:/user/login.jsp"));
        }

        List<User> users = userService.getUsers();
        return new ModelAndView(new JspView("/user/list.jsp")).addModel("users", users);
    }

    @RequestMapping(value = "/users/profile", method = RequestMethod.GET)
    public ModelAndView userProfile(HttpServletRequest request, HttpServletResponse response) {
        // String userId = request.getParameter("userId");
        String name = request.getParameter("name");

        User user = userService.getUserByUserId(name);
        return new ModelAndView(new JspView("/user/profile.jsp")).addModel("user", user);
    }

    @RequestMapping(value = "/users", method = RequestMethod.PATCH)
    public ModelAndView userUpdate(HttpServletRequest request, HttpServletResponse response) {
        try {
            User user = new User(
                    request.getParameter("userId"),
                    request.getParameter("password"),
                    request.getParameter("name"),
                    request.getParameter("email"));

            if (!user.isEmpty()) {
                userService.updateUser(user);
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
            }
            return new ModelAndView(new JsonView()).addModel("result", Result.ok());
        } catch (Exception e) {
            return new ModelAndView(new JsonView()).addModel("result", Result.fail(e.getMessage()));
        }
    }

    @RequestMapping(value = "/users/form")
    public ModelAndView userGetUpdateForm(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            Object value = session.getAttribute("user");
            if (value == null) {
                log.debug("Only logged-in users can access.");
                return new ModelAndView(new JspView("redirect:/user/login.jsp"));
            }
            User user = (User) value;
            if (!user.isSameUser(request.getParameter("userId"))) {
                log.debug("자신의 계정에만 접근할 수 있습니다.");
                return new ModelAndView(new JspView("redirect:/users"));
            }

            request.setAttribute("user", user);
            return new ModelAndView(new JspView("/user/updateform.jsp")).addModel("user", user);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
