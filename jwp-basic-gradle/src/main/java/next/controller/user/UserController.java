package next.controller.user;

import core.annotation.Controller;
import core.annotation.Inject;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.mvcframework.ModelAndView;
import core.mvcframework.controller.AbstractAnnotationController;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import next.controller.user.dto.ProfileUserDto;
import next.model.User;
import next.service.UserService;
import next.utils.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class UserController extends AbstractAnnotationController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Inject
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/users/profile", method = RequestMethod.GET)
    public ModelAndView profile(final HttpServletRequest request, HttpServletResponse response) {
        return jspView("user/profile").addObject("profileUser",
                ProfileUserDto.from(SessionUtil.getLoginObject(request.getSession(), "user"))
        );
    }

    @RequestMapping(value = "/users/signOut", method = RequestMethod.GET)
    public ModelAndView signOut(final HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        return jspView("redirect:/");
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView userList(final HttpServletRequest request, final HttpServletResponse response) {
        List<User> users = userService.findAllUsers();
        log.debug("allUserList={}", users);
        request.setAttribute("users", users);
        return jspView("user/list");
    }

    @RequestMapping(value = "/users/update", method = RequestMethod.GET)
    public ModelAndView updateUserForm(final HttpServletRequest request, final HttpServletResponse response) {
        String userId = request.getParameter("userId");
        User loginedUser = SessionUtil.getLoginObject(request.getSession(), "user");
        if (!userId.equals(loginedUser.getUserId())) {
            throw new IllegalArgumentException("[ERROR] 다른 사용자의 정보를 수정할 수 없습니다.");
        }
        return jspView("user/update");
    }

    @RequestMapping(value = "/users/update", method = RequestMethod.POST)
    public ModelAndView updateUser(final HttpServletRequest request, final HttpServletResponse response) {
        User updatedUser = new User(
                request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email")
        );
        userService.updateUserInformation(updatedUser);
        log.info("updatedUser={}", updatedUser);
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        session.setAttribute("user", updatedUser);
        return jspView("redirect:/users");
    }

}
