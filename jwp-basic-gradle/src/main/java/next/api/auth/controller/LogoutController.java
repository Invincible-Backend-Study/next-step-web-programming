package next.api.auth.controller;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.web.ModelAndView;
import next.common.view.JspView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class LogoutController {
    private static final Logger log = LoggerFactory.getLogger(LogoutController.class);

    @RequestMapping("/user/logout")
    public ModelAndView authSessionLogout(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        session.invalidate();
        return new ModelAndView(new JspView("redirect:/index"));
    }
}
