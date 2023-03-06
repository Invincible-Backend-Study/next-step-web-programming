package next.web.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import next.model.User;
import next.mvc.AbstractController;
import next.mvc.ModelAndView;

public class ProfileController extends AbstractController {

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse res) {
        HttpSession seesion = req.getSession();
        User user = (User) seesion.getAttribute("user");
        if (user == null) {
            return jspView("/user/login.jsp");
        }
        return jspView("/user/profile.jsp");
    }
}
