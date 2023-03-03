package next.api.user.controller;

import core.annotation.Controller;
import core.annotation.RequestMapping;
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
public class ListUserController {
    private static final Logger log = LoggerFactory.getLogger(ListUserController.class);
    private final UserService userService = UserService.getInstance();

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
}
