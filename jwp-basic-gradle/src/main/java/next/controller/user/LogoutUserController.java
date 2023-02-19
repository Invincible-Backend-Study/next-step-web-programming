package next.controller.user;

import core.mvcframework.controller.Controller;
import core.mvcframework.view.JspView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutUserController implements Controller {

    @Override
    public JspView execute(final HttpServletRequest request, final HttpServletResponse response) {
        request.getSession().invalidate();
        return new JspView("redirect:/");
    }
}
