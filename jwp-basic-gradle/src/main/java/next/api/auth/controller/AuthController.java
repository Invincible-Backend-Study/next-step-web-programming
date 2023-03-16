package next.api.auth.controller;

import core.annotation.Controller;
import core.annotation.Inject;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.web.ModelAndView;
import next.api.auth.service.AuthService;
import next.api.user.model.User;
import next.common.view.JspView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;
    @Inject
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @RequestMapping(value = "/auth/login", method = RequestMethod.POST)
    public ModelAndView authSessionLogin(HttpServletRequest request, HttpServletResponse response) {
        User user = getLoginUser(request.getParameter("userId"), request.getParameter("password"));
        if (user == null) {  // login failed
            return new ModelAndView(new JspView("redirect:/user/login_failed.jsp"));
        }

        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        return new ModelAndView(new JspView("redirect:/index"));
    }

    @RequestMapping("/auth/logout")
    public ModelAndView authSessionLogout(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        session.invalidate();
        return new ModelAndView(new JspView("redirect:/index"));
    }

    private User getLoginUser(String id, String password) {
        return authService.getUser(id, password);
    }
}
