package next.controller;

import core.db.DataBase;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.utils.UserUtils;

public class ListUserController implements Controller{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        if(!UserUtils.isLoggedIn(request.getSession())){
            return "redirect: /user/loginForm";
        }

        request.setAttribute("users", DataBase.findAll());
        return "/WEB-INF/user/list.jsp";
    }
}
