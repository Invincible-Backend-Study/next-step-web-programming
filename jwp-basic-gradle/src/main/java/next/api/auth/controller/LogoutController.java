package next.api.auth.controller;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.web.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import next.common.controller.AbstractController;
import next.common.view.JspView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class LogoutController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(LogoutController.class);

    @Override
    @RequestMapping("/user/logout")
    protected ModelAndView doGet(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        session.invalidate();
        return new ModelAndView(new JspView("redirect:/index"));
    }
}
