package next.web.controller;

import core.mvcframework.AbstractController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeController extends AbstractController {
    @Override
    protected String doGet(final HttpServletRequest request, final HttpServletResponse response) {
        return "home";
    }
}
