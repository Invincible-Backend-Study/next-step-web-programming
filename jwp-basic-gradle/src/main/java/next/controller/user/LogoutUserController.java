package next.controller.user;

import core.mvcframework.Controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutUserController implements Controller {

    @Override
    public String execute(final HttpServletRequest request, final HttpServletResponse response) {
        request.getSession().invalidate();
        return "redirect:/";
    }
}
