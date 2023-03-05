package next.api.user.controller;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.web.ModelAndView;
import next.api.user.model.User;
import next.api.user.service.UserService;
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
    private final UserService userService = UserService.getInstance();

    @RequestMapping(value = "/user/create", method = RequestMethod.POST)
    public ModelAndView userAdd(HttpServletRequest request, HttpServletResponse response) {
        User user = new User(
                request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email")
        );

        userService.addUser(user);
        return new ModelAndView(new JspView("redirect:/user/list"));
    }

    @RequestMapping("/user/list")
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

    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    public ModelAndView userUpdate(HttpServletRequest request, HttpServletResponse response) {
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

    @RequestMapping(value = "/user/form")
    public ModelAndView userGetUpdateForm(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Object value = session.getAttribute("user");
        if (value == null) {
            log.debug("Only logged-in users can access.");
            return new ModelAndView(new JspView("redirect:/user/login.jsp"));
        }
        User user = (User) value;
        if (!user.isSameUser(request.getParameter("userId"))) {
            log.debug("자신의 계정에만 접근할 수 있습니다.");
            return new ModelAndView(new JspView("redirect:/user/list"));
        }

        request.setAttribute("user", user);
        return new ModelAndView(new JspView("/user/update.jsp")).addModel("user", user);
    }
}