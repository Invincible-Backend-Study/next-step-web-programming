package next.controller.user.legacy;

import core.web.mvcframework.ModelAndView;
import core.web.mvcframework.controller.AbstractController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignOutController extends AbstractController {

    @Override
    public ModelAndView execute(final HttpServletRequest request, final HttpServletResponse response) {
        request.getSession().invalidate();
        return jspView("redirect:/");
    }

}
