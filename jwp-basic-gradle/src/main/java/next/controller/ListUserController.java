package next.controller;

import core.db.DataBase;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.utils.UserSessionUtils;

public class ListUserController implements Controller{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        if(!UserSessionUtils.isLoggedIn(request.getSession())){
            return "redirect: /users/loginForm";
        }

        request.setAttribute("users", DataBase.findAll());
        return "/WEB-INF/list.jsp";
    }
}
