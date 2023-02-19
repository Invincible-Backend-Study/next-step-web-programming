package next.controller.user;

import core.mvcframework.ModelAndView;
import core.mvcframework.controller.AbstractController;
import core.mvcframework.controller.Controller;
import core.mvcframework.view.JspView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutUserController extends AbstractController {

    @Override
    public ModelAndView execute(final HttpServletRequest request, final HttpServletResponse response) {
        request.getSession().invalidate();
        return jspView("redirect:/");
    }
}
