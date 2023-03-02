package next.api.auth.controller;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.web.ModelAndView;
import next.api.auth.service.AuthService;
import next.api.user.model.User;
import next.common.controller.AbstractController;
import next.common.view.JspView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    private final AuthService authService = AuthService.getInstance();

    @Override
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    protected ModelAndView doPost(HttpServletRequest request, HttpServletResponse response) {
        User user = getLoginUser(request.getParameter("userId"), request.getParameter("password"));
        if (user == null) {  // login failed
            return new ModelAndView(new JspView("redirect:/user/login_failed.jsp"));
        }

        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        return new ModelAndView(new JspView("redirect:/index"));
    }

    private User getLoginUser(String id, String password) {
        return authService.getUser(id, password);
    }
}
