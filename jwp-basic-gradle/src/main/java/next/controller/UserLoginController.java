package next.controller;

import core.db.DataBase;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserLoginController implements Controller{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        var userId = request.getParameter("userId");
        var user = DataBase.findUserById(userId);
        if(user == null) {
            return "redirect: /user/login";
        }

        if(user.comparePassword(request.getParameter("password"))){
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            return "redirect: /";
        }
        return "redirect: /user/login";
    }
}
