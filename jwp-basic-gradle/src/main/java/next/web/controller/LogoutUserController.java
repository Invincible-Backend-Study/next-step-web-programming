package next.web.controller;

import core.web.controller.AbstractController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutUserController extends AbstractController {

    @Override
    protected String doGet(final HttpServletRequest request, final HttpServletResponse response) {
        request.getSession().invalidate();
        return "redirect:/";
    }
}
