package next.controller.user;

import core.annotation.Controller;
import core.annotation.Inject;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.mvcframework.ModelAndView;
import core.mvcframework.controller.AbstractAnnotationController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import next.model.User;
import next.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class AuthController extends AbstractAnnotationController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final UserService userService;

    @Inject
    public AuthController(final UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.GET)
    public ModelAndView signUpForm(final HttpServletRequest request, final HttpServletResponse response) {
        return jspView("user/form");
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public ModelAndView signUp(final HttpServletRequest request, final HttpServletResponse response) {
        User user = new User(
                request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email")
        );
        userService.signUp(user);
        log.debug("createUser={}", user);
        return jspView("redirect:/");
    }

    @RequestMapping(value = "/signIn", method = RequestMethod.GET)
    public ModelAndView signInForm(final HttpServletRequest request, final HttpServletResponse response) {
        return jspView("user/login");
    }

    @RequestMapping(value = "/signIn", method = RequestMethod.POST)
    public ModelAndView signIn(final HttpServletRequest request, final HttpServletResponse response) {
        HttpSession session = request.getSession();
        try {
            User loginedUser = userService.loginUser(request.getParameter("userId"), request.getParameter("password"));
            if (loginedUser == null) {
                return jspView("redirect:/signInFail");
            }
            session.setAttribute("user", loginedUser);
            return jspView("redirect:/");
        } catch (IllegalArgumentException exception) {
            log.error(exception.getMessage());
            return jspView("redirect:/signInFail");
        }
    }

    @RequestMapping(value = "/singInFail", method = RequestMethod.GET)
    public ModelAndView signInFail(final HttpServletRequest request, final HttpServletResponse response) {
        return jspView("user/login_failed");
    }

}
