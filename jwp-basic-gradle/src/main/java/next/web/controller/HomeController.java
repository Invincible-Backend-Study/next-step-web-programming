package next.web.controller;

import core.web.controller.AbstractController;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeController extends AbstractController {
    @Override
    protected String doGet(final HttpServletRequest request, final HttpServletResponse response) {
        return "index";
    }
}
