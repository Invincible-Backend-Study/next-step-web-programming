package next.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import next.web.LogoutServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogoutController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(LogoutController.class);

    @Override
    protected String doGet(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        session.invalidate();
        return "redirect:/index.jsp";
    }
}
