package next.controller;

import core.db.DataBase;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserLoginController implements Controller{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        final var userId = request.getParameter("userId");
        final var user = DataBase.findUserById(userId);

        if(user == null) {
            return "/WEB-INF/user/login_failed.jsp";
        }

        if(user.comparePassword(request.getParameter("password"))){
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            return "redirect: /";
        }
        return "/WEB-INF/user/login_failed.jsp";
    }
}
