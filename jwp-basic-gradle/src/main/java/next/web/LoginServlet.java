package next.web;

import core.db.DataBase;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/user/login")
public class LoginServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getLoginUser(req.getParameter("userId"), req.getParameter("password"));
        if (user == null) {  // login failed
            resp.sendRedirect("/user/login_failed.jsp");
            return;
        }

        HttpSession session = req.getSession();
        session.setAttribute("user", user);
        resp.sendRedirect("/index.jsp");
    }

    private User getLoginUser(String id, String password) {
        User user = DataBase.findUserById(id);
        if (user == null) {
            return null;
        }
        if (user.isAuthWith(id, password)) {
            return user;
        }
        return null;
    }
}
