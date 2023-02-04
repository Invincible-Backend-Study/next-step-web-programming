package next.controller;

import core.db.DataBase;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import next.model.User;

public class LoginController extends AbstractController {
    @Override
    protected String doPost(HttpServletRequest request, HttpServletResponse response) {
        User user = getLoginUser(request.getParameter("userId"), request.getParameter("password"));
        if (user == null) {  // login failed
            return "redirect:/user/login_failed.jsp";
        }

        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        return "redirect:/index.jsp";
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
