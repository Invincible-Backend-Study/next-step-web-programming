package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeController implements Controller {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return "/";
    }
}
