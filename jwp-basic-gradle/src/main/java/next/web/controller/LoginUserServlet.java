package next.web.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import next.model.User;
import next.web.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/user/login")
public class LoginUserServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(LoginUserServlet.class);

    private final UserService userService = new UserService();

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Object loginTry = session.getAttribute("loginTry");
        if (loginTry != null && (boolean) loginTry) {
            session.removeAttribute("loginTry");
            request.getRequestDispatcher("/user/login_failed.jsp").forward(request, response);
            return;
        }

        request.getRequestDispatcher("/user/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        try {
            User loginedUser = userService.loginUser(request.getParameter("userId"), request.getParameter("password"));
            if (loginedUser == null) {
                failLogin(response, session);
                return;
            }
            session.setAttribute("user", loginedUser);
            response.sendRedirect("/");
        } catch (IllegalArgumentException exception) {
            log.error(exception.getMessage());
            failLogin(response, session);
        }
    }

    private static void failLogin(final HttpServletResponse response, final HttpSession session) throws IOException {
        session.setAttribute("loginTry", true);
        response.sendRedirect("/user/login");
    }
}
