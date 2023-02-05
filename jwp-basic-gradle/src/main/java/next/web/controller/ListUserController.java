package next.web.controller;

import core.web.controller.AbstractController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ListUserController extends AbstractController {

    @Override
    protected String doGet(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Object userObject = session.getAttribute("user");
        if (userObject != null) {
            return "user/list";
        }
        return "redirect:/user/login";
    }
}
